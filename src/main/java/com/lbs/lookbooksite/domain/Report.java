package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import com.lbs.lookbooksite.domain.timeEntities.ReportTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "report_tbl")
@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Report extends ReportTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "boardId", name = "targetBoard")
    private Board targetBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "memberId", name = "sendMember")
    private Member sendMember;


}
