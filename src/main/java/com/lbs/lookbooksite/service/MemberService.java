package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public interface MemberService extends UserDetailsService {

    // 회원가입 사용
    default Member dtoToEntity(MemberDto dto) {
        //String tags = String.join("#", dto.getStyleTag());
        LocalDate date = LocalDate.parse(dto.getBirth(), DateTimeFormatter.ISO_DATE);

        Member entity = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .name(dto.getName())
                .birth(date)
                .auth(dto.getAuth())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .build();
        return entity;
    }




    public String signup(MemberDto memberInfo);

    public void changeStyleTag(MemberDto dto);

    public void changeMemberInfo(MemberDto changeMember);

    public void changePassword(MemberDto changeMember);
}
