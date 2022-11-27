package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.configs.FileManager;
import com.lbs.lookbooksite.domain.StyleTag;
import com.lbs.lookbooksite.dto.styleTag.StyleTagDto;
import com.lbs.lookbooksite.repository.StyleTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleTagServiceImpl implements StyleTagService{

    private final StyleTagRepository styleTagRepository;

    @Value("${file.upload.styleTagImg}")
    private String filePath;
    private final FileManager fileManager;

    @Override
    public StyleTagDto getAllStyleTags() {
        List<StyleTag> findAllStyleTags = styleTagRepository.findAll();

        StyleTagDto allStyleTags = entityToDto(findAllStyleTags);

        return allStyleTags;
    }

    @Override
    @Transactional
    public String addStyleTag(StyleTagDto dto) {

        try {
            MultipartFile img = dto.getUrl();

            String originName = img.getOriginalFilename();
            int lastDot = originName.lastIndexOf(".");
            String ext = img.getOriginalFilename().substring(lastDot);

            String savedName = dto.getUrlName()+ext;

            Path savePath = Paths.get(filePath + File.separator + savedName);

            fileManager.fileUpload(img,savePath);

            int index = savePath.toString().lastIndexOf("/styleTagImg");
            String storedPath = savePath.toString().substring(index);

            StyleTag uploadEntity = StyleTag.builder()
                    .styleTag(dto.getTag())
                    .photoUrl(storedPath)
                    .build();

            return styleTagRepository.save(uploadEntity).getStyleTag();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dto.getTag();
    }
}
