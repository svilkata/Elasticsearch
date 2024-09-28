package com.demo.elasticsearch.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.demo.elasticsearch.exception.BadRequestException;
import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.service.ElasticSearchService;
import com.demo.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final ProductService productService;
    private final ElasticSearchService elasticSearchService;

    @Autowired
    public ProductController(ProductService productService, ElasticSearchService elasticSearchService) {
        this.productService = productService;
        this.elasticSearchService = elasticSearchService;
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

    @GetMapping("/matchallproducts")
    @ResponseBody
    public List<Product> matchAllProducts() throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.matchAllProducts();
        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        System.out.println(listOfHits);

        List<Product> listOfProducts = new ArrayList<>();
        for (Hit<Product> hit : listOfHits) {
            listOfProducts.add(hit.source());
        }

        return listOfProducts;
    }

    @GetMapping("/matchallproducts/{fieldValue}")
    @ResponseBody
    public List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse = elasticSearchService.matchProductsWithName(fieldValue);
        List<Hit<Product>> listOfHits = searchResponse.hits().hits();
        System.out.println(listOfHits);

        List<Product> listOfProducts = new ArrayList<>();
        for (Hit<Product> hit : listOfHits) {
            listOfProducts.add(hit.source());
        }

        return listOfProducts;
    }
}
