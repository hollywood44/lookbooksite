package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.dto.order.OrderDto;

public interface OrderService {



    String putOrder(OrderDto dto);
    String orderStatusChange();

}
