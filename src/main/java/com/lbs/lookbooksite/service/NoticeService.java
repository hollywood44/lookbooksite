package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Notice;
import com.lbs.lookbooksite.domain.Order;
import com.lbs.lookbooksite.dto.NoticeDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticeService {

    default Notice makeNotice(Member targetMember,Member sendMember,String notice) {
        Notice entity = Notice.builder()
                .notice(notice)
                .targetMember(targetMember)
                .sendMember(sendMember)
                .build();
        return entity;
    }

    void sendCommentNotice(Long boardId,Member member);
    void sendOrderNotice(Member targetMember,String orderId ,Order.OrderStatus orderStatus);
    List<NoticeDto> readNotice(Member member);

    Long checkNoti(Member member);

    List<NoticeDto> getAllNotice(Member member);

    Page<NoticeDto> getPrevNotice(Member member,int page);
}
