package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
