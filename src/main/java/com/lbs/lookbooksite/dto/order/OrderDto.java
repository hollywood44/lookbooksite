package com.lbs.lookbooksite.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String orderId;
    private String memberId;
    private String orderStatus;


    private String address;
    private String addressDetail;
    private String receiverName;

    private int totalPrice;
    private String orderDate;

    private List<OrderItemDto> orderItemDtos;
}
