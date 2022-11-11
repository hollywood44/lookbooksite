package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.board.Board_ImageDto;
import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.dto.product.Product_ImageDto;

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

    default BoardDto entityToDto(Board entity) {
        List<Board_ImageDto> dtoImages = new ArrayList<>();

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

        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .writer(entity.getWriter().getMemberId())
                .returnImages(dtoImages)
                // todo comment 추가해야함
                .build();
        return dto;
    }

    //</editor-fold>

    Long uploadBoardWithImg(BoardDto dto);

    Long uploadBoardWithOutImg(BoardDto dto);

    List<BoardDto> getAllBoardList();


}
