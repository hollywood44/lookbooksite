package com.lbs.lookbooksite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @GetMapping("/denied")
    public String deniedPage(){
        return "deniedPage";
    }
}