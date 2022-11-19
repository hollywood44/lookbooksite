package com.lbs.lookbooksite.service;

import com.lbs.lookbooksite.domain.Product;
import com.lbs.lookbooksite.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;


    @Test
    public void getPd() {
        Optional<Product> pd = productRepository.findById("T003");
        if (pd.isPresent()) {
            System.out.println(pd.get().getProductId());
        }
    }

}