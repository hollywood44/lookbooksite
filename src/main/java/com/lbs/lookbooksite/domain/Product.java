package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    // 상품 고유 아이디
    @Id
    private String productId;

    // 상품명
    @Column(length = 50)
    private String productName;

    // 상품설명
    @Column(length = 500)
    private String description;

    // 상품사진 경로
    @Column(length = 100)
    private String photoUrl;

    // 가격
    private int price;

    // 재고
    private int stock;

    @Builder.Default
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product_Image> productImages = new ArrayList<>();

    public void addImgs(Product_Image product_image) {
        productImages.add(product_image);
        product_image.setBoardId(this);
    }
}
