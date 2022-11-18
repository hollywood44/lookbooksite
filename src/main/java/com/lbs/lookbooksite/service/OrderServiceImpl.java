package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.dto.order.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    @Override
    public String putOrder(OrderDto dto) {
        return null;
    }

    @Override
    public String orderStatusChange() {
        return null;
    }
}
