package com.lbs.lookbooksite.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    /** 필드설명
     * 게시글아이디
     * 제목
     * 내용
     * 조회수
     * 글쓴이(멤버테이블)
     * 이미지 리스트(post)
     * 이미지 리스트(get)
     * 댓글 리스트(get)
     */
    private Long boardId;
    private String title;
    private String content;
    private Integer viewCount;
    private String writer;

    private List<MultipartFile> getImages;
    private List<Board_ImageDto> returnImages;
    private List<CommentDto> commentList;

    private int likeCount;
    private int commentCount;

}
