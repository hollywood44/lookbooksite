package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    @Rollback(value = false)
    void uploadBoardWithOutImg() {
        Member member = Member.builder().memberId("member1").build();
        IntStream.rangeClosed(1,53).forEach(i->{
            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }
}