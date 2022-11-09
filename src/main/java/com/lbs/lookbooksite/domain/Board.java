package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board  extends BaseTimeEntity {

    // 게시글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    // 제목
    @Column(length = 50)
    private String title;

    // 내용
    @Column(length = 500)
    private String content;

    // 게시글 사진 경로
    @Column(length = 100)
    private String photoUrl;

    // 조회수
    private int viewCount;

    // 글쓴이
    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member writer;

    // 해당 게시글 이미지 리스트
    @Builder.Default
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board_Image> boardImgs = new ArrayList<>();

    // 해당 게시물 댓글
    @Builder.Default
    @OneToMany(mappedBy = "commentId")
    private List<Comment> commentList = new ArrayList<>();

    public void addImgs(Board_Image board_image) {
        boardImgs.add(board_image);
        board_image.setBoardId(this);
    }


    //===============================비즈니스 로직===============================//

    // 조회수 증가
    public void plusViewCount() {
        this.viewCount = this.viewCount + 1;
    }

}
