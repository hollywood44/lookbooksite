package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.cart.CartDto;
import com.lbs.lookbooksite.repository.CartItemRepository;
import com.lbs.lookbooksite.repository.CartRepository;
import com.lbs.lookbooksite.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository itemRepository;
    private final ProductRepository productRepository;



    @Override
    public Long itemAddToCart(String productId, int itemCount, Member loginedMember) {
        Optional<Cart> cartCheck = cartRepository.findByMemberId(loginedMember);
        if (cartCheck.isPresent()) {
            Cart cart = cartCheck.get();
            Product item = productRepository.findById(productId).get();
            Optional<CartItem> joinedItem = itemRepository.findByProductId(item);

            if (joinedItem.isPresent()) {
                itemCount += joinedItem.get().getItemCount();
                CartItem cartItem = joinedItem.get();
                cartItem.changeItemCount(itemCount);
                cart.addItem(cartItem);
            } else {
                CartItem cartItem = CartItem.builder().itemCount(itemCount).productId(item).build();
                cart.addItem(cartItem);
            }

            return cartRepository.save(cart).getCartId();
        }

        return null;
    }

    @Override
    public CartDto getMyCart(Member loginedMember) {
        Optional<Cart> cartCheck = cartRepository.findByMemberId(loginedMember);
        if (cartCheck.isPresent()) {
            Cart cart = cartCheck.get();
            CartDto getCart = entityToDto(cart);
            return getCart;
        }

        return null;
    }

    @Override
    @Transactional
    public List<Long> cartItemDelete(List<Long> cartItemList) {
        for (Long id : cartItemList) {
            itemRepository.deleteById(id);
        }
        return cartItemList;
    }
}
