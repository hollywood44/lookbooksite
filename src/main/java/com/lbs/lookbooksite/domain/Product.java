package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
