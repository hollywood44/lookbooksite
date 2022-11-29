package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.order.OrderItemDto;
import com.lbs.lookbooksite.repository.CartItemRepository;
import com.lbs.lookbooksite.repository.CartRepository;
import com.lbs.lookbooksite.repository.OrderRepository;
import com.lbs.lookbooksite.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

                Product forStockChangeProduct = product.get();
                forStockChangeProduct.changeStock(dtoItem.getItemCount());

                orderRepository.save(order);
                productRepository.save(forStockChangeProduct);
            }
        }

        for (OrderItemDto dtoItem : dto.getOrderItemDtos()) {
            cartItemRepository.deleteById(dtoItem.getCartItemId());
        }

        return order.getOrderId();
    }

    @Override
    @Transactional
    public Page<OrderDto> getMyOrder(Member loginedMember, int page) {
        Function<Order, OrderDto> fn = (entity -> (entityToDtoForGetMyOrder(entity)));

        Sort sort = Sort.by("orderDate").descending();
        Pageable pageable = PageRequest.of(page,10,sort); // page(번호)부터 10개씩 잘라서 보겠다

        Page<Order> entityList = orderRepository.findByMemberId(loginedMember,pageable);
        Page<OrderDto> orderList = entityList.map(fn);

        return orderList;
    }

    @Override
    public String orderStatusChange() {
        return null;
    }
}
