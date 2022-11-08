package com.lbs.lookbooksite.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class MemberDto {

    private String memberId;
    private String password;
    private String email;
    private String phone;
    private String name;
    private LocalDate birth;
    private String auth;
    private String gender;

    private String addressNumber;
    private String address;
    private String addressDetail;

    private String[] styleTag;



}
