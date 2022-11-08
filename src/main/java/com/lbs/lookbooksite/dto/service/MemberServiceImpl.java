package com.lbs.lookbooksite.dto.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;



    //  ================================Override==================================
    // security에서 이용하는 메서드
    // username(userId)를 가지고 db에서 값을 찾아옴 // 자동으로 password를 비교해주는 기능이 있다
    @Override
    public Member loadUserByUsername(String memberId) throws UsernameNotFoundException {
        return repository.findById(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));
    }
}
