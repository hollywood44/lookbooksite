package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "member_tbl")
@Builder
@ToString(exclude = {"myCart","myOrderList","myNoticeList"})
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements UserDetails {

    //<editor-fold desc="필드">

    // 회원 아이디
    @Id
    @Column(length = 20)
    private String memberId;

    // 비밀번호
    private String password;

    // 이메일
    @Column(length = 50)
    private String email;

    // 휴대전화 번호
    @Column(length = 20)
    private String phone;

    // 이름
    @Column(length = 20)
    private String name;

    // 생일
    private LocalDate birth;

    // 권한
    @Column(length = 30)
    private String auth;

    // 성별
    @Column(length = 10)
    private String gender;

    // 우편번호
    @Column(length = 20)
    private String addressNumber;

    // 주소
    @Column(length = 100)
    private String address;

    // 상세주소(몇동 몇호 같은)
    @Column(length = 100)
    private String addressDetail;

    // 스타일태그
    // todo 스타일태그 테이블에 있는것만 들어갈 수 있도록 프론트에서 조절
    // todo 고른 항목이 #miniaml#street... 이런 식으로 들어가서 #으로 쪼개서 배열에 담을 수 있게 만들기
    @Column(length = 150)
    private String styleTag;

    @OneToOne(mappedBy = "memberId",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Cart myCart;

    @Builder.Default
    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Order> myOrderList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "noticeId", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<Notice> myNoticeList = new ArrayList<>();

    //</editor-fold>

    //<editor-fold desc="시큐리티 관련">
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 아이디 반환
    @Override
    public String getUsername() {
        return memberId;
    }

    // 비밀번호 반환
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //</editor-fold>

    //<editor-fold desc="간단한 비즈니스 로직">

    public void changeStyleTag(String tag) {
        this.styleTag = tag;
    }

    public void changePassword(String pw) {
        this.password = pw;
    }

    public void changeMemberInfo(String email, String phone, String name, String addressNumber, String address, String addressDetail) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.addressNumber = addressNumber;
        this.address = address;
        this.addressDetail = addressDetail;
    }

    //</editor-fold>


}
