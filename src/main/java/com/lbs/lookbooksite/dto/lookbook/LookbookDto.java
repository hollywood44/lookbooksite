package com.lbs.lookbooksite.dto.lookbook;

import com.lbs.lookbooksite.dto.board.Board_ImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LookbookDto {

    private Long lookbookId;

    @NotEmpty(message = "제목은 필수사항입니다.")
    private String lookbookTitle;

    @NotEmpty(message = "브랜드명은 필수사항입니다.")
    private String brand;

    @NotEmpty(message = "설명은 필수사항입니다.")
    private String description;

    @NotEmpty(message = "스타일태그는 필수사항입니다.")
    private String styleTag;

    @NotEmpty(message = "이미지는 필수사항입니다.")
    private List<MultipartFile> getImages;
    private List<Lookbook_ImageDto> returnImages;
}
