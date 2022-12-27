package com.example.kaushop.controller;

import com.example.kaushop.entity.Product;
import com.example.kaushop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/v1/product/{productId}")
    public ResponseEntity<Product> getProduct(
            @PathVariable("productId") long productId
    ) {
        Product product = productService.getProduct(productId);

        return ResponseEntity.ok(product);
    }
}
