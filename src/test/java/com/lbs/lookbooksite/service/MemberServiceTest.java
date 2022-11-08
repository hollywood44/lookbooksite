package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;


    @Test
    @Rollback(value = false)
    void adminSignUp() {
        String[] tags = {"minimal","street"};

        MemberDto dto = MemberDto.builder()
                .memberId("admin")
                .password("1234")
                .email("admin@lookbooksite.com")
                .phone("010-0000-0000")
                .name("admin")
                .birth(LocalDate.of(2022,11,8))
                .auth("ROLE_ADMIN,ROLE_MEMBER")
                .gender("MALE")
                .addressNumber("00000")
                .address("경상북도 경산시 경산읍 경산길")
                .addressDetail("룩북빌딩 505호")
                .styleTag(tags)
                .build();

        System.out.println(memberService.signup(dto));
    }

    @Test
    @Rollback(value = false)
    void userSignUp() {
        String[] tags = {"minimal","street"};

        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .password("1234")
                .email("member1@lookbooksite.com")
                .phone("010-1111-1111")
                .name("member1")
                .birth(LocalDate.of(2022,11,8))
                .auth("ROLE_MEMBER")
                .gender("MALE")
                .addressNumber("11111")
                .address("경상북도 경산시 경산읍 경산길")
                .addressDetail("우리집 101호")
                .styleTag(tags)
                .build();

        System.out.println(memberService.signup(dto));
    }
}