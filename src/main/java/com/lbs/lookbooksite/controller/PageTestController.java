package com.lbs.lookbooksite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageTestController {

    @GetMapping("/testPage")
    public String testPage() {
        return "test/forTest";
    }
}
