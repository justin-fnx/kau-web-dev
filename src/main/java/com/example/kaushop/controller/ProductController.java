package com.example.kaushop.controller;

import com.example.kaushop.entity.Product;
import com.example.kaushop.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

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
        Optional<Product> optProduct = productService.getProduct(productId);

        if(optProduct.isPresent()) {
            return ResponseEntity.ok(optProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/v1/products")
    public ResponseEntity<Iterable<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("/v1/product")
    public ResponseEntity createProduct(
            @RequestBody Product product
    ) {
        product.setId(null);

        Product newProduct = productService.createProduct(product);
        URI newProductUri = URI.create("/api/v1/product/" + newProduct.getId());

        return ResponseEntity.created(newProductUri).build();
    }

}
