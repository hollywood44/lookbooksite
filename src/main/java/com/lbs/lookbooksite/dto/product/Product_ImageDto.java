package com.lbs.lookbooksite.dto.product;

import com.lbs.lookbooksite.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product_ImageDto {

    private Long imageId;
    private String storedName;
    private String originName;
    private String storedPath;
    private String product;

}
