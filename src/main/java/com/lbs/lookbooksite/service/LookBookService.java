package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.LookBook;
import com.lbs.lookbooksite.domain.LookBook_Image;
import com.lbs.lookbooksite.dto.lookbook.LookbookDto;
import com.lbs.lookbooksite.dto.lookbook.Lookbook_ImageDto;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public interface LookBookService {

    //<editor-fold desc="entity <-> dto">

    default LookBook dtoToEntity(LookbookDto dto) {
        LookBook entity = LookBook.builder()
                .lookbookTitle(dto.getLookbookTitle())
                .brand(dto.getBrand())
                .description(dto.getDescription())
                .styleTag(dto.getStyleTag())
                .build();
        return entity;
    }

    default LookbookDto entityToDto(LookBook entity) {
        List<Lookbook_ImageDto> dtoImages = new ArrayList<>();

        if (!entity.getLookbookImages().isEmpty()) {
            for (LookBook_Image entityImg : entity.getLookbookImages()) {
                Lookbook_ImageDto dtoImg = Lookbook_ImageDto.builder()
                        .imageId(entityImg.getImageId())
                        .storedPath(entityImg.getStoredPath())
                        .build();
                dtoImages.add(dtoImg);
            }
        } else {
            dtoImages = null;
        }

        LookbookDto dto = LookbookDto.builder()
                .lookbookId(entity.getLookbookId())
                .lookbookTitle(entity.getLookbookTitle())
                .brand(entity.getBrand())
                .description(entity.getDescription())
                .styleTag(entity.getStyleTag())
                .returnImages(dtoImages)
                .build();
        return dto;
    }


    //</editor-fold>

    Long postingWithImg(LookbookDto dto);

    Page<LookbookDto> getAllLookbook(String styleTag,int page);

    LookbookDto getLookbookDetail(Long lookbookId);

}
