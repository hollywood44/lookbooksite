package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.StyleTag;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;
import com.lbs.lookbooksite.repository.StyleTagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StyleTagServiceTest {

    @Autowired
    private StyleTagService styleTagService;
    @Autowired
    private StyleTagRepository repository;

    @Test
    void getAllTagsTest() {
        StyleTagDto dto = styleTagService.getAllStyleTags();

        System.out.println("************");
        System.out.println(dto);
    }

    @Test
    void getUploadPath() {

    }


}