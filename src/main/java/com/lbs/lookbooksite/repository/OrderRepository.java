package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,String> {
}
