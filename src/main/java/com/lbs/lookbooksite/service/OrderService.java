package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Order;
import com.lbs.lookbooksite.domain.OrderItem;
import com.lbs.lookbooksite.domain.Product;
import com.lbs.lookbooksite.dto.order.OrderDto;
import com.lbs.lookbooksite.dto.order.OrderItemDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public interface OrderService {


    // orderitem빼고 생성 orderItem 은 구현한 서비스쪽에서 주입해줄것
    default Order orderReadyForDtoToEntity(OrderDto dto,Member member) {
        LocalDate dateNow = LocalDate.now();
        LocalDateTime timeNow = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        String nDate = dateNow.format(dateFormatter);
        String nTime = timeNow.format(timeFormatter);
        Random random = new Random();
        String randomChar = random.ints(97,123)
                .limit(2)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String makeId = nDate + nTime + randomChar;

        Order entity = Order.builder()
                .orderId(makeId)
                .memberId(member)
                .orderStatus(Order.OrderStatus.Ready)
                .receiverName(dto.getReceiverName())
                .address(dto.getAddress())
                .addressDetail(dto.getAddressDetail())
                .totalPrice(dto.getTotalPrice())
                .build();
        return entity;
    }


    String putOrder(OrderDto dto, Member member);
    String orderStatusChange();

}
