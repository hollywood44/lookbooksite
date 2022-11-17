package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Cart;
import com.lbs.lookbooksite.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart> findByMemberId(Member memberId);
}
