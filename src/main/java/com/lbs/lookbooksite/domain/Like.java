package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "like_tbl")
@Builder
@ToString(exclude = "targetBoard")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    // 좋아요달릴 게시글 아이디
    @ManyToOne(targetEntity = Board.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board targetBoard;

    // 해당 게시글에 좋아요 한 회원아이디
    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member likedMember;

}
