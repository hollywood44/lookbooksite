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

    default MemberDto entityToDto(Member entity) {
        String date = entity.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        MemberDto dto = MemberDto.builder()
                .memberId(entity.getMemberId())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .name(entity.getName())
                .birth(date)
                .gender(entity.getGender())
                .address(entity.getAddress())
                .addressDetail(entity.getAddressDetail())
                .build();
        return dto;
    }




    // 회원가입
    public String signup(MemberDto memberInfo);
    // 회원 정보 가져오기
    public MemberDto getMyInfo(Member member);
    // 스타일태그 변경
    public void changeStyleTag(MemberDto dto);
    // 멤버 정보 수정
    public String changeMemberInfo(MemberDto changeMember);
}
