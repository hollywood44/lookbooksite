package com.lbs.lookbooksite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminController {

    @GetMapping("/main")
    public String adminMainPage() {
        return "/admin/admin_page";
    }
}
