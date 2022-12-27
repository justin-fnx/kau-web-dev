package com.example.kaushop.service;

import com.example.kaushop.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public Product getProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("상품" + id);

        return product;
    }
}
