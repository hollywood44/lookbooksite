package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;
import com.lbs.lookbooksite.service.BoardService;
import com.lbs.lookbooksite.service.MemberService;
import com.lbs.lookbooksite.service.StyleTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final StyleTagService styleTagService;
    private final BoardService boardService;

    //<editor-fold desc="GET">

    @GetMapping("main")
    public String mainPage() {
        return "main_page";
    }

    @GetMapping("/signIn")
    public String loginPage() {
        return "member/personal/signIn_page";
    }

    @GetMapping("/signUp")
    public String signUpPage(MemberDto memberDto) {
        return "member/personal/signUp_page";
    }

    @GetMapping("/denied")
    public String deniedPage(){
        return "deniedPage";
    }

    @GetMapping("/my-info")
    public String myInfoPage(Model model, @AuthenticationPrincipal Member loginedMember) {
        model.addAttribute("memberDto",memberService.getMyInfo(loginedMember));

        return "member/personal/myInfo_page";
    }

    @GetMapping("/modify")
    public String modifyPage(Model model, @AuthenticationPrincipal Member loginedMember) {
        model.addAttribute("memberDto",memberService.getMyInfo(loginedMember));

        return "member/personal/memberModify_page";
    }

    @GetMapping("/styleTag")
    public String myStyleTag(Model model,@AuthenticationPrincipal Member member) {
        // 모든 스타일 태그 해쉬맵
        Map<String,String> allTagList = styleTagService.getAllStyleTags().getStyleTag();

        // 내가 선호하는 스타일 태그
        List<String> myStyleTagList = memberService.getMyStyleTag(member);

        if (!myStyleTagList.isEmpty()) {
            // 선호하지 않는 태그 해쉬맵
            Map<String, String> notLikeTags = new HashMap<>(allTagList);
            for (String like : myStyleTagList) {
                notLikeTags.remove(like);
            }
            // 선호하는 태그 해쉬맵
            Map<String, String> likeTags = new HashMap<>();
            for (String like : myStyleTagList) {
                String likeSoPut = allTagList.get(like);
                likeTags.put(like, likeSoPut);
            }

            model.addAttribute("likeTags", likeTags);
            model.addAttribute("notLikeTags", notLikeTags);
        } else {
            model.addAttribute("notLikeTags",allTagList);
        }

        return "member/personal/myStyleTag_page";
    }

    @GetMapping("/myBoard")
    public String myBoardList(Model model,@AuthenticationPrincipal Member loginedMember,@RequestParam(value = "page", defaultValue = "1") int page) {
        page = page -1;

        Page<BoardDto> paging = boardService.getMyBoardList(page,loginedMember);

        model.addAttribute("paging", paging);
        model.addAttribute("maxPage",5);

        return "member/board/myBoard_page";
    }

    //</editor-fold>

    //<editor-fold desc="POST">
    @PostMapping("/signUp")
    public String signUp(@Valid MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/personal/signUp_page";
        }
        memberDto.setAuth("ROLE_MEMBER");
        memberDto.setStyleTag("");

        try {
            memberService.signup(memberDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "member/personal/signUp_page";
        }catch(Exception e) {
            System.out.println("controller" + memberDto);
            e.printStackTrace();
            bindingResult.reject("signupFailed", "error");
            return "member/personal/signUp_page";
        }

        return "redirect:/member/main";
    }

    @PostMapping("/modify")
    public String memberInfoModify(@Valid MemberDto memberDto, BindingResult bindingResult, @AuthenticationPrincipal Member loginedMember) {
        if (bindingResult.hasErrors()) {
            return "member/personal/memberModify_page";
        }

        String status = memberService.changeMemberInfo(memberDto);

        if (status.equals("phone")) {
            bindingResult.rejectValue("phone", "already phone number has exist","이미 있는 휴대전화 번호 입니다.");
            return "member/personal/memberModify_page";
        }

        return "redirect:/member/modify";
    }
    //</editor-fold>

    @PostMapping("/styleTag")
    public String modifyStyleTag(@RequestParam(value = "styleTag", defaultValue = "") List<String> styleTag,@AuthenticationPrincipal Member loginedMember) {

        if (!styleTag.isEmpty()) {
            memberService.changeStyleTag(styleTag,loginedMember);
            return "redirect:/member/styleTag";
        } else {
            return "redirect:/member/styleTag";
        }
    }
}
