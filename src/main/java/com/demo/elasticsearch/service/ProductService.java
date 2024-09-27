package com.demo.elasticsearch.service;

import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepo;

    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Iterable<Product> getAllProducts() {
        return this.productRepo.findAll();
    }

    public Product insertProduct(Product product) {
        return this.productRepo.save(product);
    }
}
