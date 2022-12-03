package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Test
    void testDSL() {
        Pageable pageable = PageRequest.of(0,10);
//        Page<Board> result = boardRepository.search("member7", "writer", pageable);
//        Page<Board> result = boardRepository.search("con", "content", pageable);
//        Page<Board> result = boardRepository.search("tit", "title", pageable);
        Page<Board> result = boardRepository.search("member7", "all", pageable);
        if (!result.isEmpty()) {
            for (Board board : result) {
                System.out.println("보드 아이디 : "+board.getBoardId());
                System.out.println("보드 제목 : "+board.getTitle());
                System.out.println("보드 내용 : "+board.getContent());
                System.out.println("보드 쓴이 : "+board.getWriter().getMemberId());
            }
        }
    }
}