package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Notice;
import com.lbs.lookbooksite.dto.NoticeDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {

    void sendCommentNotice(Long boardId,Member member);
    List<NoticeDto> readNotice(Member member);

    Long checkNoti(Member member);

    List<NoticeDto> getAllNotice(Member member);

    Page<NoticeDto> getPrevNotice(Member member,int page);
}
