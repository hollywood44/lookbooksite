package com.lbs.lookbooksite.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "styletag_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StyleTag {

    // 스타일 태그
    @Id
    @Column(length = 30)
    private String styleTag;

    // 해당 태그의 대표 소개 사진
    private String photoUrl;
}
