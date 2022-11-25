package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    //<editor-fold desc="GET">

    @GetMapping("main")
    public String mainPage() {
        return "main_page";
    }

    @GetMapping("/signIn")
    public String loginPage() {
        return "member/signIn_page";
    }

    @GetMapping("/signUp")
    public String signUpPage(MemberDto memberDto) {
        return "member/signUp_page";
    }

    @GetMapping("/denied")
    public String deniedPage(){
        return "deniedPage";
    }

    @GetMapping("/my-info")
    public String myInfoPage(Model model, @AuthenticationPrincipal Member loginedMember) {
        model.addAttribute("memberDto",memberService.getMyInfo(loginedMember));

        return "member/myInfo_page";
    }

    @GetMapping("/modify")
    public String modifyPage(Model model, @AuthenticationPrincipal Member loginedMember) {
        model.addAttribute("memberDto",memberService.getMyInfo(loginedMember));

        return "member/memberModify_page";
    }

    //</editor-fold>

    //<editor-fold desc="POST">
    @PostMapping("/signUp")
    public String signUp(@Valid MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "member/signUp_page";
        }
        memberDto.setAuth("ROLE_MEMBER");

        try {
            memberService.signup(memberDto);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "member/signUp_page";
        }catch(Exception e) {
            System.out.println("controller" + memberDto);
            e.printStackTrace();
            bindingResult.reject("signupFailed", "error");
            return "member/signUp_page";
        }

        return "redirect:/member/main";
    }

    @PostMapping("/modify")
    public String memberInfoModify(@Valid MemberDto memberDto, BindingResult bindingResult, @AuthenticationPrincipal Member loginedMember) {
        if (bindingResult.hasErrors()) {
            return "member/memberModify_page";
        }

        String status = memberService.changeMemberInfo(memberDto);

        if (status.equals("phone")) {
            bindingResult.rejectValue("phone", "already phone number has exist","이미 있는 휴대전화 번호 입니다.");
            return "member/memberModify_page";
        }

        return "redirect:/member/modify";
    }
    //</editor-fold>


}
