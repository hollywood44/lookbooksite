package com.lbs.lookbooksite.configs;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
}