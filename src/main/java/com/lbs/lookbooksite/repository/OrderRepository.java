package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Member;
import com.lbs.lookbooksite.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {

    Page<Order> findByMemberId(Member loginedMember, Pageable pageable);

    Page<Order> findByOrderStatus(Order.OrderStatus orderStatus, Pageable pageable);
}
