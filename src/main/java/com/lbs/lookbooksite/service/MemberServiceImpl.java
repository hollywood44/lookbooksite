package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Cart;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.repository.CartRepository;
import com.lbs.lookbooksite.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository repository;
    private final CartRepository cartRepository;

    // 회원가입
    @Override
    public String signup(MemberDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));
        Member member = dtoToEntity(dto);
        repository.save(member);

        Cart cart = Cart.builder().memberId(member).build();
        cartRepository.save(cart);

        return member.getMemberId();
    }

    @Override
    public MemberDto getMyInfo(Member loginedMember) {
        System.out.println(loginedMember);

        Member member = repository.findById(loginedMember.getMemberId()).orElseThrow(()-> new UsernameNotFoundException("아이디를 찾을 수 없습니다."));

        MemberDto myInfo = entityToDto(member);

        return myInfo;
    }

    // 스타일 태그 변경
    @Override
    public void changeStyleTag(List<String> tagList,Member loginedMember) {
        Member member = repository.findById(loginedMember.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));

        String tags = String.join(",",tagList);

        member.changeStyleTag(tags);

        repository.save(member);
    }

    @Override
    public List<String> getMyStyleTag(Member member) {
        Member loginedMember = repository.findById(member.getMemberId()).get();

        String checkNull = loginedMember.getStyleTag();
        if (checkNull.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<String> myStyleTagList = new ArrayList<>(Arrays.asList(loginedMember.getStyleTag().split(",")));
            return myStyleTagList;
        }
    }

    // 회원 정보 변경
    @Override
    @Transactional
    public String changeMemberInfo(MemberDto changeInfo) {
        Member member = repository.findById(changeInfo.getMemberId())
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다!"));

        Optional<Member> checkPhone = repository.findByPhone(changeInfo.getPhone());

        if (!checkPhone.isPresent() || member.getPhone().equals(changeInfo.getPhone())) {
            member.changeMemberInfo(changeInfo.getEmail(), changeInfo.getPhone(), changeInfo.getName(),
                    changeInfo.getAddress(), changeInfo.getAddressDetail());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            changeInfo.setPassword(encoder.encode(changeInfo.getPassword()));
            member.changePassword(changeInfo.getPassword());

            return repository.save(member).getMemberId();
        } else {
            return "phone"; // 존재하는 휴대폰 번호
        }
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
