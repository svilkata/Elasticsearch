package com.demo.elasticsearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.demo.elasticsearch.repository")
//public class ElasticConfiguration extends ElasticsearchConfiguration {
//
//
//    @Override
//    public ClientConfiguration clientConfiguration() {
//        return ClientConfiguration.builder()
//                .connectedToLocalhost()//"localhost:9200"
//                .usingSsl()
//                .withBasicAuth("elastic", "123")
//                .build();
//    }
//}
