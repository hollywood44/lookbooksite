package com.lbs.lookbooksite.controller;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.MemberDto;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.board.CommentDto;
import com.lbs.lookbooksite.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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

    // 게시글 리스트(paging x)
//    @GetMapping("/list")
//    public String boardListPage(Model model) {
//        List<BoardDto> allBoard = boardService.getAllBoardList();
//        model.addAttribute("allBoard", allBoard);
//        return "boardListTest";
//    }

    // 게시글 리스트(paging o)
    @GetMapping("/list")
    public String boardListPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        page = page -1;
        Page<BoardDto> paging = boardService.getAllBoardList(page);

        model.addAttribute("paging", paging);
        model.addAttribute("maxPage",10);

        return "member/board/boardList_page";
    }



    // 게시글 상세보기
    @GetMapping("/detail/{boardId}")
    public String boardDetail(Model model, @PathVariable("boardId") Long boardId) {
        BoardDto board = boardService.getBoard(boardId);
        model.addAttribute("board", board);
        return "/member/board/boardDetail_page";
    }

    // 게시글 수정 페이지
    @GetMapping("/modify/{boardId}")
    public String boardModifyPage(Model model,@PathVariable("boardId") Long boardId) {
        BoardDto board = boardService.getBoardAsModify(boardId);
        board.setContent(board.getContent().replace("<br>","\n"));
        model.addAttribute("boardDto", board);

        return "member/board/boardModify_page";
    }

    // 게시글 이미지 삭제
    @GetMapping("/delete-Img")
    public String deleteImage(@RequestParam("imageId")Long imageId,@RequestParam("boardId") Long boardId) {
        boardService.deleteImage(imageId);
        return "redirect:/board/modify/"+boardId;
    }

    // 게시글 좋아요
    @GetMapping("/like/{boardId}")
    public String boardLike(@PathVariable("boardId") Long boardId,@AuthenticationPrincipal Member loginedMember) {
        boardService.likeBoard(loginedMember,boardId);

        return String.format("redirect:/board/detail/%s", boardId);
    }

    // 게시글 쓰기 페이지
    @GetMapping("/upload")
    public String uploadBoardPage(BoardDto boardDto) {
        return "member/board/boardPost_page";
    }

    // 게시글 등록
    @PostMapping("/upload")
    public String uploadBoard(@Valid BoardDto boardDto, BindingResult bindingResult, @AuthenticationPrincipal Member loginedMember) {
        // title content 비어있는지 체크
        if (bindingResult.hasErrors()) {
            return "member/board/boardPost_page";
        }

        // 들어온 파일이 이미지가 아니거나, 비어있을 경우 체크
        int checkFileIsNull = 0;
        for (MultipartFile img : boardDto.getGetImages()) {
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
        boardDto.setWriter(loginedMember.getMemberId());

        boardDto.setContent(boardDto.getContent().replace("\n","<br>"));


        if (checkFileIsNull == 0) {
            boardService.uploadBoardWithOutImg(boardDto);
        } else {
            boardService.uploadBoardWithImg(boardDto);
        }

        return "redirect:/board/list";
    }

    // 게시글 수정
    @PostMapping("/modify")
    public String modifyBoard(@Valid BoardDto boardDto, BindingResult bindingResult,@AuthenticationPrincipal Member loginedMember) {
        if (bindingResult.hasErrors()) {
            return "member/board/modify/"+boardDto.getBoardId();
        }

        // 들어온 파일이 이미지가 아니거나, 비어있을 경우  =0 , 있을경우 =1
        int checkFileIsNull = 0;
        for (MultipartFile img : boardDto.getGetImages()) {
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

        boardDto.setContent(boardDto.getContent().replace("\n","<br>"));

        boardService.modifyBoard(boardDto,checkFileIsNull);

        return "redirect:/board/detail/"+boardDto.getBoardId();
    }


    // 댓글 등록
    @PostMapping("/comment/{boardId}")
    public String postComment(@PathVariable("boardId") Long boardId, CommentDto commentDto,@AuthenticationPrincipal Member loginedMember) {
        boardService.postComment(commentDto,loginedMember,boardId);

        return String.format("redirect:/board/detail/%s", boardId);
    }
}
