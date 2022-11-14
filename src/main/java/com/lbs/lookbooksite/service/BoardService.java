package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.board.Board_ImageDto;
import com.lbs.lookbooksite.dto.board.CommentDto;
import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.dto.product.Product_ImageDto;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface BoardService {

    //<editor-fold desc="entity <-> dto">
    default Board dtoToEntity(BoardDto dto) {
        Board entity = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(Member.builder().memberId(dto.getWriter()).build())
                .build();
        return entity;
    }

    // 상세보기에서 쓸거
    default BoardDto entityToDto(Board entity) {
        List<Board_ImageDto> dtoImages = new ArrayList<>();
        List<CommentDto> commentDtoList = new ArrayList<>();

        if (!entity.getBoardImgs().isEmpty()) {
            for (Board_Image entityImg : entity.getBoardImgs()) {
                Board_ImageDto dtoImg = Board_ImageDto.builder()
                        .imageId(entityImg.getImageId())
                        .storedPath(entityImg.getStoredPath())
                        .build();
                dtoImages.add(dtoImg);
            }
        } else {
            dtoImages = null;
        }

        if (entity.getCommentCount() > 0) {
            for (Comment cEntity : entity.getCommentList()) {
                CommentDto cDto = CommentDto.builder()
                        .comment(cEntity.getComment())
                        .commenter(cEntity.getCommenter().getMemberId())
                        .build();
                commentDtoList.add(cDto);
            }
        } else {
            commentDtoList = null;
        }

        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .writer(entity.getWriter().getMemberId())
                .returnImages(dtoImages)
                .commentCount(entity.getCommentCount())
                .likeCount(entity.getLikeCount())
                .commentList(commentDtoList)
                .regDate(entity.getRegDate())
                .regDate(entity.getModDate())
                .build();
        return dto;
    }

    // 리스트보기에서 쓸거
    default BoardDto entityToDtoNoneDetail(Board entity) {
        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .viewCount(entity.getViewCount())
                .writer(entity.getWriter().getMemberId())
                .commentCount(entity.getCommentCount())
                .likeCount(entity.getLikeCount())
                .build();
        return dto;
    }

    //</editor-fold>

    Long uploadBoardWithImg(BoardDto dto);

    Long uploadBoardWithOutImg(BoardDto dto);

    List<BoardDto> getAllBoardList();

    BoardDto getBoard(Long boardId);

    void likeBoard(Member member, Long boardId);

    void postComment(CommentDto commentDto,Member commenter,Long boardId);

    Page<BoardDto> getAllBoardList(int page);
}
