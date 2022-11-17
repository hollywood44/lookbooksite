package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.CartItem;
import com.lbs.lookbooksite.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(Product productId);
}
