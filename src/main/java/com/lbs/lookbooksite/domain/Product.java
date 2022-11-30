package com.lbs.lookbooksite.domain;

import com.lbs.lookbooksite.domain.timeEntities.BaseTimeEntity;
import com.lbs.lookbooksite.dto.product.ProductDto;
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

    // 가격
    private int price;

    // 재고
    private int stock;

    @Builder.Default
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Product_Image> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "productId",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "productId",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CartItem> cartItems;

    //<editor-fold desc="메소드">

    // 상품업로드만으로 사진도 같이 업로드 하려고 사용
    // 상품이미지를 추가하고, 상품이미지 엔티티에 product에 해당 상품 세팅
    public void addImgs(Product_Image product_image) {
        productImages.add(product_image);
        product_image.setBoardId(this);
    }

    // 재고 변경
    public void changeStock(int stockC){
        this.stock = this.stock-stockC;
    }


    // 상품 수정
    public void modifyProduct(ProductDto modify) {
        this.productName = modify.getProductName();
        this.description = modify.getDescription();
        this.price = modify.getPrice();
        this.stock = modify.getStock();
    }

    // 상품 이미지 삭제
    public void deleteImgs() {
        productImages.clear();
    }

    //</editor-fold>

}
