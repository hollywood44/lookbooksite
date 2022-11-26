package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.domain.Board;
import com.lbs.lookbooksite.domain.LookBook;
import com.lbs.lookbooksite.domain.LookBook_Image;
import com.lbs.lookbooksite.domain.Product_Image;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.lookbook.LookbookDto;
import com.lbs.lookbooksite.dto.lookbook.Lookbook_ImageDto;
import com.lbs.lookbooksite.repository.LookBookRepository;
import com.lbs.lookbooksite.repository.LookBook_ImageRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class LookBookServiceImpl implements LookBookService {

    private final LookBookRepository lookBookRepository;
    private final LookBook_ImageRepository imageRepository;
    private final FileManager fileManager;

    //<editor-fold desc="포스팅관련">
    @Value("${file.upload.lookBookImg}")
    private String uploadPath;

    public String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }

        return folderPath;
    }

    @Override
    @Transactional
    public Long postingWithImg(LookbookDto dto) {

        LookBook postingLB = dtoToEntity(dto);

        try {
            for (MultipartFile img : dto.getGetImages()) {
                // 실제 파일명
                String originName = img.getOriginalFilename();
                // 랜덤 파일명 생성
                String uuid = UUID.randomUUID().toString();
                // 저장될 파일명(랜덤파일명_실제파일명)
                String savedName = uuid + "_" + originName;
                // 저장할 위치경로
                Path savePath = Paths.get(uploadPath + File.separator + makeFolder() + File.separator + savedName);

                // 파일 업로드
                fileManager.fileUpload(img, savePath);

                // 썸네일 생성 (나중에 썸네일 가지고 오고 싶을때
                // lastindexof "_" 위치를 "_s_"로 replace해서 사용)
                String thumbnailSaveName = uploadPath + File.separator + makeFolder() + File.separator + uuid + "_s_" + originName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 200, 200);

                // /~~Img/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                int index = savePath.toString().lastIndexOf("/lookBookImg");
                String storedPath = savePath.toString().substring(index);

                LookBook_Image lookBook_image = LookBook_Image.builder()
                        .storedName(savedName)
                        .originName(originName)
                        .storedPath(storedPath)
                        .build();
                // 영속성 전이
                postingLB.addImgs(lookBook_image);
            }
            //DB에 저장
            return lookBookRepository.save(postingLB).getLookbookId();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Long modifyLookbook(LookbookDto modify, int checkStatus) {

        LookBook modifyLB = lookBookRepository.findById(modify.getLookbookId()).get();
        modifyLB.modifyWithOutImg(modify);
        if (checkStatus == 1) {
            modifyLB.deleteAllImgs();
            try {
                for (MultipartFile img : modify.getGetImages()) {
                    // 실제 파일명
                    String originName = img.getOriginalFilename();
                    // 랜덤 파일명 생성
                    String uuid = UUID.randomUUID().toString();
                    // 저장될 파일명(랜덤파일명_실제파일명)
                    String savedName = uuid + "_" + originName;
                    // 저장할 위치경로
                    Path savePath = Paths.get(uploadPath + File.separator + makeFolder() + File.separator + savedName);

                    // 파일 업로드
                    fileManager.fileUpload(img, savePath);

                    // /~~Img/**로 사용하기 쉽게  (/boardImg/년/월/일/파일명)으로 저장
                    int index = savePath.toString().lastIndexOf("/lookBookImg");
                    String storedPath = savePath.toString().substring(index);

                    LookBook_Image lookBook_image = LookBook_Image.builder()
                            .storedName(savedName)
                            .originName(originName)
                            .storedPath(storedPath)
                            .build();
                    // 영속성 전이
                    modifyLB.addImgs(lookBook_image);
                }
                //DB에 저장
                return lookBookRepository.save(modifyLB).getLookbookId();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return lookBookRepository.save(modifyLB).getLookbookId();
    }

    @Override
    public Long deleteLookbookImg(Long imageId) {
        LookBook_Image img = imageRepository.findById(imageId).get();
        imageRepository.delete(img);

        return imageId;
    }

    //</editor-fold>

    //<editor-fold desc="룩북 가져오기 관련">

    @Override
    public Page<LookbookDto> getAllLookbook(String styleTag,int page) {
        Function<LookBook, LookbookDto> fn = (entity -> (entityToDto(entity)));
        Sort sort = Sort.by("lookbookId").descending();
        Pageable pageable = PageRequest.of(page,10,sort); // page(번호)부터 10개씩 잘라서 보겠다
        if (styleTag.equals("all")) {
            Page<LookBook> eAllLookbookList = lookBookRepository.findAll(pageable);
            Page<LookbookDto> dAllLookbookList = eAllLookbookList.map(fn);
            return dAllLookbookList;
        } else {
            Page<LookBook> eAllLookbookList = lookBookRepository.findByStyleTagContains(styleTag,pageable);
            Page<LookbookDto> dAllLookbookList = eAllLookbookList.map(fn);
            return dAllLookbookList;
        }
    }

    @Override
    public LookbookDto getLookbookDetail(Long lookbookId) {
        Optional<LookBook> entity = lookBookRepository.findById(lookbookId);
        if (entity.isPresent()) {
            LookbookDto detail = entityToDto(entity.get());
            return detail;
        } else {
            return null;
        }

    }

    //</editor-fold>

}
