package com.demo.elasticsearch.controller;

import com.demo.elasticsearch.exception.BadRequestException;
import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    @ResponseBody
    public Iterable<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping()
    @ResponseBody
    public Product insertProduct(@RequestBody Product product) {
        return productService.insertProduct(product);
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        if (id != product.getId()) {
            throw new BadRequestException("Id differs!");
        }
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productService.deleteProductById(id);
    }
}
