package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.NoticeDto;
import com.lbs.lookbooksite.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping
    public String checkNoticeAndMoveNoticePage(@AuthenticationPrincipal Member member, Model model) {
        List<NoticeDto> nonReadNotice = noticeService.readNotice(member);
        List<NoticeDto> readNotice = noticeService.getAllNotice(member);
        if (!nonReadNotice.isEmpty()) {
            readNotice.removeAll(nonReadNotice);
            model.addAttribute("nonReadNotice",nonReadNotice);
        }
        model.addAttribute("readNotice",readNotice);
        return "/member/notice_page";
    }
}
