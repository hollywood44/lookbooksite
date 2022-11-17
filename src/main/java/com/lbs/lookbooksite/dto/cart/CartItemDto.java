package com.lbs.lookbooksite.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
/* 멤버 설명
* 카트아이템 고유 아이디
* 해당 아이템이 담긴 카트 아이디
* 상품 아이디
* 상품 이름
* 상품 가격
* 몇개 담겨있는지
* */
    private Long cartItemId;

    private Long cartId;

    private String productId;
    private String productName;
    private String storedPath;
    private int productPrice;
    private int itemCount;
}
