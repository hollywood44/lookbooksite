package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comment_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    // 댓글 고유 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 댓글 내용
    @Column(length = 50)
    private String comment;

    // 댓글 쓴 회원
    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member commenter;

    // 댓글이 달릴 게시글 아이디
    @ManyToOne(targetEntity = Board.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board targetBoard;


}
