package com.lbs.lookbooksite.configs;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileManager {
    // 경로가 문자열로 들어올경우
    public void fileUpload(MultipartFile file, String filePath) throws IOException {
        file.transferTo(new File(filePath));
    }
    // 경로가 패스로 들어올경우
    public void fileUpload(MultipartFile file, Path savePath)throws IOException{
        file.transferTo(savePath);
    }

    // 빈 파일이 들어오거나, 이미지가 아닌 파일이 들어오면 0반환 아니면 1반환
    public int fileCheckIsNullOrNotImg(MultipartFile file) {
        int checkStatus = 0;

        File checkFile = new File(file.getOriginalFilename());
        String type = null;

        try {
            type = Files.probeContentType(checkFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (file.isEmpty() || !type.startsWith("image")) {
            checkStatus = 0;
        } else {
            checkStatus = 1;
        }

        return checkStatus;
    }
}