package com.lbs.lookbooksite.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@Builder
public class MemberDto {

    @NotEmpty(message = "아이디는 필수항목입니다.")
    private String memberId;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotEmpty(message = "전화번호는 필수항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "000-000(0)-0000 형태의 전화번호를 입력해 주십시오")
    private String phone;

    @NotEmpty(message = "이름은 필수항목입니다.")
    private String name;

    @NotEmpty(message = "생년월일은 필수항목입니다.")
    private String birth;

    private String auth;

    @NotEmpty(message = "성별은 필수항목입니다.")
    private String gender;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String address;

    @NotEmpty(message = "주소는 필수항목입니다.")
    private String addressDetail;

    private String[] styleTag;



}
