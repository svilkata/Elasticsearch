package com.demo.elasticsearch.service;

import com.demo.elasticsearch.exception.ProductNotFoundException;
import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Product updateProduct(int id, Product product) {
        Optional<Product> optProduct = this.productRepo.findById(id);
        if (optProduct.isPresent()) {
            Product productToUpdate = optProduct.get();
            //we update only quantity and price
            productToUpdate.setQuantity(product.getQuantity());
            productToUpdate.setPrice(product.getPrice());

            return this.productRepo.save(productToUpdate);
        } else {
            throw new ProductNotFoundException();
        }
    }

    public void deleteProductById(int id) {
        this.productRepo.deleteById(id);
    }
}
