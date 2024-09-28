package com.demo.elasticsearch.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.demo.elasticsearch.service.ElasticSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api")
public class SearchController {
    private final ElasticSearchService elasticSearchService;

    @Autowired
    public SearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }


    // Searches in all indexes (documents/tables)
    @GetMapping("/matchall")
    @ResponseBody
    public String matchAll() throws IOException {
        SearchResponse<Map> searchResponse = elasticSearchService.matchAllService();
        String result = searchResponse.hits().hits().toString();
        System.out.println(result);
        return result;
    }
}
