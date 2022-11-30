package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.NoticeTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(referencedColumnName = "memberId",name = "targetMember")
    private Member targetMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberId",name = "sendMember")
    private Member sendMember;

    private String notice;

    private LocalDateTime readDate;

    public void readNotice() {
        this.readDate = LocalDateTime.now();
    }

}
