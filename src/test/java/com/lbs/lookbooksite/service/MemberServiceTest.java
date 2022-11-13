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

    // 특정 회원정보 보기
    @Test
    public void getMemberInfo() {
        Member member = memberRepository.findById("member1").get();

        System.out.println("*******************************");
        System.out.println(member);
    }


    //<editor-fold desc="회원가입 테스트">
    @Test
    @Rollback(value = false)
    void adminSignUp() {
        String[] tags = {"minimal", "street"};

        MemberDto dto = MemberDto.builder()
                .memberId("admin")
                .password("1234")
                .email("admin@lookbooksite.com")
                .phone("010-0000-0000")
                .name("admin")
                .auth("ROLE_ADMIN,ROLE_MEMBER")
                .gender("MALE")
                .address("경상북도 경산시 경산읍 경산길")
                .addressDetail("룩북빌딩 505호")
                .styleTag(tags)
                .build();

        System.out.println(memberService.signup(dto));
    }

    @Test
    @Rollback(value = false)
    void userSignUp() {
        String[] tags = {"minimal", "street"};

        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .password("1234")
                .email("member1@lookbooksite.com")
                .phone("010-1111-1111")
                .name("member1")
                .auth("ROLE_MEMBER")
                .gender("MALE")
                .address("경상북도 경산시 경산읍 경산길")
                .addressDetail("우리집 101호")
                .styleTag(tags)
                .build();

        System.out.println(memberService.signup(dto));
    }
    //</editor-fold>

    //<editor-fold desc="회원정보 변경 테스트">

    // 스타일태그 변경 테스트
    @Test
    @Rollback(value = false)
    public void changeStyleTagTest() {
        String[] tags = {"minimal", "american_casual"};

        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .styleTag(tags)
                .build();

        memberService.changeStyleTag(dto);
    }

    // 회원 정보 수정(비밀번호 변경 x 경우)
    @Test
    @Rollback(value = false)
    public void changeMemberInfoWithoutPW(){
        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .email("member1@lookbooksite.com")
                .phone("010-1111-1111")
                .name("member1")
                .address("경상북도 경산시 경산읍 경산길")
                .addressDetail("우리집 201호")
                .build();

        memberService.changeMemberInfo(dto);
    }

    // 회원 정보 수정(비밀번호 변경 o 경우)
    @Test
    @Rollback(value = false)
    public void changeMemberInfoIncludePW(){
        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .password("12345")
                .build();

        memberService.changePassword(dto);
    }

    //</editor-fold>

}