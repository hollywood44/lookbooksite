package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Product;
import com.lbs.lookbooksite.domain.Product_Image;
import com.lbs.lookbooksite.dto.product.ProductDto;
import com.lbs.lookbooksite.dto.product.Product_ImageDto;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    //<editor-fold desc="entity <-> dto">

    default Product dtoToEntity(ProductDto dto) {
        Product entity = Product.builder()
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .build();

        return entity;
    }


    default ProductDto entityToDTO(Product entity) {
        List<Product_ImageDto> dtoImages = new ArrayList<>();

        for (Product_Image entityImg : entity.getProductImages()) {
            Product_ImageDto dtoImg = Product_ImageDto.builder()
                    .imageId(entityImg.getImageId())
                    .storedPath(entityImg.getStoredPath())
                    .build();
            dtoImages.add(dtoImg);
        }

        ProductDto dto = ProductDto.builder()
                .productId(entity.getProductId())
                .productName(entity.getProductName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .returnImages(dtoImages)
                .build();
        return dto;
    }

    //</editor-fold>

    String uploadProductWithImg(ProductDto dto);
    String uploadProductWithOutImg(ProductDto dto);

    List<ProductDto> getAllProductList();

    ProductDto getProduct(String productId);

    String modifyProduct(ProductDto dto);
    String modifyProductWithOutImg(ProductDto dto);
}
