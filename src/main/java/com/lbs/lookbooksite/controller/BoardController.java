package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;


@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String boardPage() { return "boardTest"; }

    @GetMapping("/list")
    public String boardListPage(Model model) {
        List<BoardDto> allBoard = boardService.getAllBoardList();
        model.addAttribute("allBoard", allBoard);
        return "boardListTest"; // todo
    }

    @GetMapping("/detail/{boardId}")
    public String boardDetail(Model model, @PathVariable("boardId") Long boardId) {
        BoardDto board = boardService.getBoard(boardId);
        model.addAttribute("board", board);

        return "board_detail";
    }

    @GetMapping("/like/{boardId}")
    public String boardLike(@PathVariable("boardId") Long boardId,@AuthenticationPrincipal Member loginedMember) {
        boardService.likeBoard(loginedMember,boardId);

        return String.format("redirect:/board/detail/%s", boardId);
    }

    @PostMapping("/upload")
    public String uploadBoard(BoardDto uploadBoard, @AuthenticationPrincipal Member loginedMember) {
        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : uploadBoard.getGetImages()) {
            File checkfile = new File(img.getOriginalFilename());
            String type = null;
            try {
                type = Files.probeContentType(checkfile.toPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (img.isEmpty() || !type.startsWith("image")) {
                checkFileIsNull = 0;
            } else {
                checkFileIsNull = 1;
            }
        }
        uploadBoard.setWriter(loginedMember.getMemberId());

        if (checkFileIsNull == 0) {
            boardService.uploadBoardWithOutImg(uploadBoard);
        } else {
            boardService.uploadBoardWithImg(uploadBoard);
        }

        return "redirect:/board/list";
    }
}
