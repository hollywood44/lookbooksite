package com.lbs.lookbooksite.dto.cart;

import com.lbs.lookbooksite.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long cartId;
    private List<CartItemDto> cartItems;
}
