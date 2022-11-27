package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.StyleTag;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;

import java.util.List;

public interface StyleTagService {

    default StyleTagDto entityToDto(List<StyleTag> entityList) {
        StyleTagDto dto = new StyleTagDto();

        for (StyleTag entity : entityList) {
            dto.getStyleTag().put(entity.getStyleTag(), entity.getPhotoUrl());
        }

        return dto;
    }

    // 모든 스타일 태그 불러와서 dto로 변환한다음 리턴
    StyleTagDto getAllStyleTags();

    String addStyleTag(StyleTagDto dto);


}
