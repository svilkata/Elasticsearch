package com.demo.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.demo.elasticsearch.model.Product;
import com.demo.elasticsearch.util.ElasticsearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class ElasticSearchService {

    private final ElasticsearchClient elasticsearchClient;
    private final ElasticsearchOperations elasticsearchOperations;
    private final IndexOperations indexOperations;

    @Autowired
    public ElasticSearchService(ElasticsearchClient elasticsearchClient, ElasticsearchOperations elasticsearchOperations, IndexOperations indexOperations) {
        this.elasticsearchClient = elasticsearchClient;
        this.elasticsearchOperations = elasticsearchOperations;
        this.indexOperations = indexOperations;
    }

    public void createDeleteIndex() {
        indexOperations.create();
        indexOperations.putMapping();
        indexOperations.delete();
    }

    // Searches in all indexes (documents/tables)
    public SearchResponse<Map> matchAllService() throws IOException {
        Supplier<Query> supplier = ElasticsearchUtil.supplier();
        SearchResponse<Map> searchResponse = elasticsearchClient.search(s -> s.query(supplier.get()), Map.class);
        System.out.println("elsticsearch query is " + supplier.get().toString());

//        elasticsearchClient.delete();
//        elasticsearchClient.create();

//        elasticsearchOperations.search((org.springframework.data.elasticsearch.core.query.Query) supplier.get(), Map.class);

        return searchResponse;
    }

    public SearchResponse<Product> matchAllProducts() throws IOException {
        Supplier<Query> supplier = ElasticsearchUtil.supplier();
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s.index("product_index").query(supplier.get()), Product.class);
        System.out.println("Elasticsearch query is " + supplier.get().toString());

        return searchResponse;
    }

    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
        Supplier<Query> supplier = ElasticsearchUtil.supplierWithName(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s.index("product_index").query(supplier.get()), Product.class);
        System.out.println("Elasticsearch query is " + supplier.get().toString());

        return searchResponse;
    }

    public SearchResponse<Product> boolQueryImpl(String productNameValue, Integer quantity) throws IOException {
        Supplier<Query> supplier = ElasticsearchUtil.supplierBool(productNameValue, quantity);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s.index("product_index").query(supplier.get()), Product.class);
        System.out.println("Elasticsearch query is " + supplier.get().toString());

        return searchResponse;
    }
}
