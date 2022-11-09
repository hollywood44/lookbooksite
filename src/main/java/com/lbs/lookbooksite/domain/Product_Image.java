package com.lbs.lookbooksite.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_image_tbl")
@Builder
@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String storedName;

    private String originName;

    private String storedPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    public void setBoardId(Product product) {
        this.product = product;
    }
}
