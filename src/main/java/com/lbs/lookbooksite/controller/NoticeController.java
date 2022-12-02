package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.NoticeDto;
import com.lbs.lookbooksite.service.NoticeService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "/member/notice/notice_page";
    }

    @GetMapping("/previousNotice")
    public String prevNoticePage(@AuthenticationPrincipal Member member, Model model,@RequestParam(value = "page", defaultValue = "1") int page) {

        Page<NoticeDto> prevNotice = noticeService.getPrevNotice(member, page);

        model.addAttribute("prevNotice", prevNotice);
        model.addAttribute("maxPage", 5);

        return "member/notice/prevNotice_page";
    }
}
