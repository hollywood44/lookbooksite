package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.*;
import com.lbs.lookbooksite.dto.board.BoardDto;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.order.OrderItemDto;
import com.lbs.lookbooksite.repository.*;
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
    private final NoticeService noticeService;


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
    @Transactional
    public OrderDto getForProcessingOrder(String orderId) {
        Order entityOrder = orderRepository.findById(orderId).get();
        OrderDto dtoOrder = entityToDtoForGetMyOrder(entityOrder);

        return dtoOrder;
    }

    @Override
    public Page<OrderDto> getAllOrderCaseByStatus(String orderStatus,int page) {
        Function<Order,OrderDto> fn = (entity->
                OrderDto.builder().orderId(entity.getOrderId()).memberId(entity.getMemberId().getMemberId()).orderStatus(entity.getOrderStatus().toString()).build());

        Sort sort = Sort.by("orderDate").descending();
        Pageable pageable = PageRequest.of(page,30,sort);

        Page<Order> entityOrder = Page.empty();

        switch (orderStatus) {
            case "ready":
                entityOrder = orderRepository.findByOrderStatus(Order.OrderStatus.Ready, pageable);
                break;
            case "complete":
                entityOrder = orderRepository.findByOrderStatus(Order.OrderStatus.Complete, pageable);
                break;
            case "cancel":
                entityOrder = orderRepository.findByOrderStatus(Order.OrderStatus.Cancel, pageable);
                break;
        }
        Page<OrderDto> orderList = entityOrder.map(fn);

        return orderList;
    }

    @Override
    @Transactional
    public String orderCompleteForAdmin(String orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.changeOrderStatus(Order.OrderStatus.Complete);

        orderRepository.save(order).getOrderId();

        noticeService.sendOrderNotice(order.getMemberId(),orderId,Order.OrderStatus.Complete);

        return orderId;
    }

    @Override
    @Transactional
    public String orderCancelForAdmin(String orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.changeOrderStatus(Order.OrderStatus.Cancel);

        orderRepository.save(order).getOrderId();

        noticeService.sendOrderNotice(order.getMemberId(),orderId,Order.OrderStatus.Cancel);

        return orderId;
    }

    @Override
    public String orderStatusChange() {
        return null;
    }
}
