package com.lbs.lookbooksite.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProceeding {

    private String memberId;

    private int totalPrice;

    private List<Long> cartItemId;
    private List<String> itemImagePath;
    private List<String> itemName;
    private List<String> productId;
    private List<Integer> itemPrice;
    private List<Integer> itemCount;

}
