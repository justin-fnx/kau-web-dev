package com.example.kaushop.service;

import com.example.kaushop.entity.Product;
import com.example.kaushop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean addStock(long productId, int stockChanged) {
        Optional<Product> optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty()) {
            return false;
        }

        Product product = optProduct.get();
        if(product.getStock() + stockChanged < 0) {
            return false;
        }
        product.setStock(product.getStock() + stockChanged);

        try {
            productRepository.save(product);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
}
