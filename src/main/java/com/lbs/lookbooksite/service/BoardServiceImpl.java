package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.board.CommentDto;
import com.lbs.lookbooksite.repository.*;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    //<editor-fold desc="기본 설정 메소드, 멤버">
    private final FileManager fileManager;

    @Value("${file.upload.boardImg}")
    private String boardFilePath;

    private final BoardRepository boardRepository;
    private final Board_ImageRepository imageRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(boardFilePath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }
    
    //</editor-fold>

    //<editor-fold desc="업로드관련">

    @Override @Transactional
    public Long uploadBoardWithImg(BoardDto dto) {

        // 이미지파일들 빼고 엔티티에 들어가 있는 상태
        Board uploadBoard = dtoToEntity(dto);

        try {
            for (MultipartFile img : dto.getGetImages()) {
                // 실제 파일명
                String originName = img.getOriginalFilename();
                // 랜덤 파일명 생성
                String uuid = UUID.randomUUID().toString();
                // 저장될 파일명(랜덤파일명_실제파일명)
                String savedName = uuid + "_" + originName;
                // 저장할 위치경로
                Path savePath = Paths.get(boardFilePath + File.separator + makeFolder() + File.separator + savedName);

                // 파일 업로드
                fileManager.fileUpload(img, savePath);

                // 썸네일 생성 (나중에 썸네일 가지고 오고 싶을때
                // lastindexof "_" 위치를 "_s_"로 replace해서 사용)
                String thumbnailSaveName = boardFilePath + File.separator + makeFolder() + File.separator + uuid + "_s_" + originName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);

                // /boardImg/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                int index = savePath.toString().lastIndexOf("/boardImg");
                String storedPath = savePath.toString().substring(index);

                Board_Image board_image = Board_Image.builder()
                        .storedName(savedName)
                        .originName(originName)
                        .storedPath(storedPath)
                        .build();
                // 영속성 전이
                uploadBoard.addImgs(board_image);
            }
            //DB에 저장
            return boardRepository.save(uploadBoard).getBoardId();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Long uploadBoardWithOutImg(BoardDto dto) {
        System.out.println("이미지 포함 안함");

        Board board = dtoToEntity(dto);

        return boardRepository.save(board).getBoardId();
    }

    @Override
    @Transactional
    public Long modifyBoard(BoardDto dto,int checkImgStatus) {

        // 이미지파일들 빼고 엔티티에 들어가 있는 상태
        Board uploadBoard = boardRepository.findById(dto.getBoardId()).get();
        // 게시글 제목,내용 수정
        uploadBoard.modify(dto);
        if(checkImgStatus==1){
            try {
                //원래 있던 이미지 삭제
                imageRepository.delete(uploadBoard.getBoardImgs().get(0));
                uploadBoard.getBoardImgs().clear();

                for (MultipartFile img : dto.getGetImages()) {
                    // 실제 파일명
                    String originName = img.getOriginalFilename();
                    // 랜덤 파일명 생성
                    String uuid = UUID.randomUUID().toString();
                    // 저장될 파일명(랜덤파일명_실제파일명)
                    String savedName = uuid + "_" + originName;
                    // 저장할 위치경로
                    Path savePath = Paths.get(boardFilePath + File.separator + makeFolder() + File.separator + savedName);

                    // 파일 업로드
                    fileManager.fileUpload(img, savePath);

                    // 썸네일 생성 (나중에 썸네일 가지고 오고 싶을때
                    // lastindexof "_" 위치를 "_s_"로 replace해서 사용)
                    String thumbnailSaveName = boardFilePath + File.separator + makeFolder() + File.separator + uuid + "_s_" + originName;
                    File thumbnailFile = new File(thumbnailSaveName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);

                    // /boardImg/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                    int index = savePath.toString().lastIndexOf("/boardImg");
                    String storedPath = savePath.toString().substring(index);

                    Board_Image board_image = Board_Image.builder()
                            .storedName(savedName)
                            .originName(originName)
                            .storedPath(storedPath)
                            .build();
                    // 영속성 전이
                    uploadBoard.addImgs(board_image);
                }

                //DB에 저장
                return boardRepository.save(uploadBoard).getBoardId();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            return boardRepository.save(uploadBoard).getBoardId();
        }
        return null;
    }

    @Override
    public void deleteImage(Long imageId) {
        Board_Image deleteImg = imageRepository.findById(imageId).get();
        imageRepository.delete(deleteImg);
    }

    //</editor-fold>

    //<editor-fold desc="가져오기 관련">

    @Override
    public Page<BoardDto> getAllBoardList(int page) {
        Function<Board, BoardDto> fn = (entity -> (entityToDtoNoneDetail(entity)));
        Sort sort = Sort.by("boardId").descending();
        Pageable pageable = PageRequest.of(page,10,sort); // page(번호)부터 10개씩 잘라서 보겠다
        Page<Board> entityList = boardRepository.findAll(pageable);

        Page<BoardDto> boardList = entityList.map(fn);
        return boardList;
    }

    @Override
    public Page<BoardDto> getSearchedBoardList(String condition,String keyword,int page) {
        Function<Board, BoardDto> fn = (entity -> (entityToDtoNoneDetail(entity)));
        Pageable pageable = PageRequest.of(page,10);
        Page<Board> entityList = boardRepository.search(keyword, condition, pageable);
        Page<BoardDto> boardList = entityList.map(fn);

        return boardList;
    }

    @Override
    public Page<BoardDto> getMyBoardList(int page,Member loginedMember) {
        Function<Board, BoardDto> fn = (entity -> (entityToDtoNoneDetail(entity)));
        Sort sort = Sort.by("boardId").descending();
        Pageable pageable = PageRequest.of(page,10,sort); // page(번호)부터 10개씩 잘라서 보겠다
        Page<Board> entityList = boardRepository.findByWriter(loginedMember,pageable);
        Page<BoardDto> boardList = entityList.map(fn);

        return boardList;
    }

    @Override
    public BoardDto getBoard(Long boardId) {
        Optional<Board> entity = boardRepository.findById(boardId);
        if (entity.isPresent()) {
            entity.get().plusViewCount();
            boardRepository.save(entity.get());
            BoardDto board = entityToDto(entity.get());
            return board;
        } else {
            return null;
        }
    }

    @Override
    public BoardDto getBoardAsModify(Long boardId) {
        Board entity = boardRepository.findById(boardId).get();
        BoardDto dto = entityToDtoAsModify(entity);
        return dto;
    }

    //</editor-fold>

    public void likeBoard(Member member, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        Optional<Like> like = likeRepository.findByTargetBoardAndLikedMember(board, member);

        if (like.isPresent()) {
            likeRepository.deleteById(like.get().getLikeId());
        } else {
            Like newLike = Like.builder()
                    .likedMember(member)
                    .targetBoard(board)
                    .build();
            likeRepository.save(newLike);
        }
    }

    @Override
    public void postComment(CommentDto commentDto,Member commenter,Long boardId) {
        Board board = boardRepository.findById(boardId).get();

        Comment putComment = Comment.builder()
                .comment(commentDto.getComment())
                .commenter(commenter)
                .targetBoard(board)
                .build();

        commentRepository.save(putComment);
    }

    @Override
    public Long reportBoard(Member member, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        Optional<Report> report = reportRepository.findByTargetBoardAndSendMember(board, member);

        if (report.isPresent()) {
            reportRepository.deleteById(report.get().getReportId());
        } else {
            Report newReport = Report.builder()
                    .targetBoard(board)
                    .sendMember(member)
                    .build();
            reportRepository.save(newReport);
        }
        return boardId;
    }

    @Override
    @Transactional
    public Long deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);

        return boardId;
    }
}
