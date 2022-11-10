package com.lbs.lookbooksite.dto.product;

import com.lbs.lookbooksite.domain.Product_Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productId;
    private String productName;
    private String description;
    private int price;
    private int stock;

    private List<MultipartFile> getImages;
    private List<Product_ImageDto> returnImages;

}
