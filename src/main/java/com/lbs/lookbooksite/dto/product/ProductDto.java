package com.lbs.lookbooksite.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    @NotEmpty(message = "상품코드는 필수사항입니다.")
    private String productId;

    @NotEmpty(message = "상품명은 필수사항입니다.")
    private String productName;
    @NotEmpty(message = "상품설명은 필수사항입니다.")
    private String description;
    @Min(value=0, message="가격은 필수사항입니다")
    private int price;
    @Min(value=0, message="재고는 필수사항입니다")
    private int stock;

    private List<MultipartFile> getImages;
    private List<Product_ImageDto> returnImages;

}
