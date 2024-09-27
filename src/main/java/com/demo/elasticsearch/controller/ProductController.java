package com.demo.elasticsearch.controller;

import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping()
    public Product insertProducts(@RequestBody Product product) {
        return productService.insertProduct(product);
    }
}
