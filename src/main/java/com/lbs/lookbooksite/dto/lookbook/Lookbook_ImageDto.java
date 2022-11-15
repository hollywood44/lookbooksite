package com.lbs.lookbooksite.dto.lookbook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lookbook_ImageDto {

    /**필드설명
     * 이미지 아이디
     * 저장된 파일명
     * 원래 파일명
     * 저장된 경로
     * 해당 이미지 가진 룩북아이디
     */
    private Long imageId;
    private String storedName;
    private String originName;
    private String storedPath;
    private Long lookbookId;
}
