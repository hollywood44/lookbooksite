package com.lbs.lookbooksite.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    private Long orderItemId;
    private String orderId;
    private String productId;
    private int itemCount;

}
