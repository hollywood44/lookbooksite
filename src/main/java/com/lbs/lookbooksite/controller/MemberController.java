package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
}
