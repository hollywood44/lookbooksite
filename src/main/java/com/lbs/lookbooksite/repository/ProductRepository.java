package com.lbs.lookbooksite.repository;

import com.lbs.lookbooksite.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {
}
