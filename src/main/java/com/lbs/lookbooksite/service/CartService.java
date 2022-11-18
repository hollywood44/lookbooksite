package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Cart;
import com.lbs.lookbooksite.domain.CartItem;
import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.dto.cart.CartDto;
import com.lbs.lookbooksite.dto.cart.CartItemDto;

import java.util.ArrayList;
import java.util.List;

public interface CartService {


    default CartDto entityToDto(Cart entity) {
        List<CartItemDto> dtoItemList = new ArrayList<>();

        for (CartItem entityItem : entity.getCartItems()) {
            CartItemDto dtoItem = CartItemDto.builder()
                    .productId(entityItem.getProductId().getProductId())
                    .productName(entityItem.getProductId().getProductName())
                    .storedPath(entityItem.getProductId().getProductImages().get(0).getStoredPath())
                    .productPrice(entityItem.getProductId().getPrice())
                    .itemCount(entityItem.getItemCount())
                    .cartItemId(entityItem.getCartItemId())
                    .build();
            dtoItemList.add(dtoItem);
        }

        CartDto dto = CartDto.builder()
                .cartId(entity.getCartId())
                .cartItems(dtoItemList)
                .build();
        return dto;
    }

    Long itemAddToCart(String productId, int itemCount, Member loginedMember);

    CartDto getMyCart(Member loginedMember);
}
