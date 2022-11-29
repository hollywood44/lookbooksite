package com.lbs.lookbooksite.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;
    private String orderId;
    private String productName;

    // 주문 시 받을 값 -> 3개
    private Long cartItemId;
    private String productId;
    private int itemCount;
}
