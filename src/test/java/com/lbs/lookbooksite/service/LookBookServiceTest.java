package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.LookBook;
import com.lbs.lookbooksite.repository.LookBookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LookBookServiceTest {

    @Autowired
    LookBookRepository lookBookRepository;


    @Test
    public void imgDelTest() {
        Long lookbookId = 1L;

        LookBook testlb = lookBookRepository.findById(lookbookId).get();
        testlb.deleteImg(5L);

        lookBookRepository.save(testlb);


    }

}