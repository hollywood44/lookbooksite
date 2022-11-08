package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface MemberService extends UserDetailsService {

    default Member dtoToEntity(MemberDto dto) {
        String tags = String.join("#", dto.getStyleTag());

        Member entity = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .name(dto.getName())
                .birth(dto.getBirth())
                .auth(dto.getAuth())
                .gender(dto.getGender())
                .addressNumber(dto.getAddressNumber())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .styleTag(tags)
                .build();
        return entity;
    }




    public String signup(MemberDto memberInfo);

    public void changeStyleTag(MemberDto dto);

    public void changeMemberInfo(MemberDto changeMember);

    public void changePassword(MemberDto changeMember);
}
