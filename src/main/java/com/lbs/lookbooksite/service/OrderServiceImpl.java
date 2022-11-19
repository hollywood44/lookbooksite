package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Order;
import com.lbs.lookbooksite.domain.OrderItem;
import com.lbs.lookbooksite.domain.Product;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.order.OrderItemDto;
import com.lbs.lookbooksite.repository.CartItemRepository;
import com.lbs.lookbooksite.repository.CartRepository;
import com.lbs.lookbooksite.repository.OrderRepository;
import com.lbs.lookbooksite.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    @Transactional
    public String putOrder(OrderDto dto, Member member) {
        Order order = orderReadyForDtoToEntity(dto,member);

        for (OrderItemDto dtoItem : dto.getOrderItemDtos()) {

            Optional<Product> product = productRepository.findById(dtoItem.getProductId());

            if (product.isPresent()) {
                OrderItem entityItem = OrderItem.builder()
                        .productId(product.get())
                        .itemCount(dtoItem.getItemCount())
                        .build();
                order.addItem(entityItem);
            }
        }

        orderRepository.save(order);

        for (OrderItemDto dtoItem : dto.getOrderItemDtos()) {
            cartItemRepository.deleteById(dtoItem.getCartItemId());
        }

        return order.getOrderId();
    }

    @Override
    public String orderStatusChange() {
        return null;
    }
}
