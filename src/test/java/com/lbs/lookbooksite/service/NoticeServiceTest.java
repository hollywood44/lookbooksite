package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    public void getNoti() {
        Member member = Member.builder()
                .memberId("member7")
                .build();
        System.out.println(noticeService.checkNoti(member));
    }

}