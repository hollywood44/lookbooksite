package com.lbs.lookbooksite.dto.styleTag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StyleTagDto {

    private String tag;

    private MultipartFile url;
    private String urlName;

    // <스타일태그,해당 태그의 대표 사진>
    @Builder.Default
    private Map<String,String> styleTag = new HashMap<>();


}
