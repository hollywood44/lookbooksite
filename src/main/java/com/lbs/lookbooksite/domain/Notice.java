package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.NoticeTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notice_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends NoticeTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member targetMember;

    private String notice;

}
