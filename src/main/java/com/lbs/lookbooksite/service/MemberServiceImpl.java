package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;


    // 회원가입
    @Override
    public String signup(MemberDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));
        return repository.save(dtoToEntity(dto)).getMemberId();
    }

    // 스타일 태그 변경
    @Override
    public void changeStyleTag(MemberDto dto) {
        Member member = repository.findById(dto.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));

        String tags = String.join("#", dto.getStyleTag());
        member.changeStyleTag(tags);

        repository.save(member);
    }

    // 회원 정보 변경
    @Override
    public void changeMemberInfo(MemberDto changeInfo) {
        Member member = repository.findById(changeInfo.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));

        member.changeMemberInfo(changeInfo.getEmail(), changeInfo.getPhone(), changeInfo.getName(), changeInfo.getAddressNumber(), changeInfo.getAddress(), changeInfo.getAddressDetail());

        repository.save(member);

    }

    // 비밀번호 변경
    @Override
    public void changePassword(MemberDto changeMember) {
        Member member = repository.findById(changeMember.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        changeMember.setPassword(encoder.encode(changeMember.getPassword()));

        member.changePassword(changeMember.getPassword());
        repository.save(member);
    }

    // <editor-fold desc="시큐리티 관련 오버라이드">
    // username(userId)를 가지고 db에서 값을 찾아옴 // 자동으로 password를 비교해주는 기능이 있다
    @Override
    public Member loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return repository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));
    }
    //</editor-fold>

}
