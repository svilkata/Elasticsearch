package com.demo.elasticsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ElasticsearchUtil {

    public static Supplier<Query> supplier() {
        return () -> Query.of(q -> q.matchAll(matchAllQuery()));
    }

    public static MatchAllQuery matchAllQuery() {
        MatchAllQuery.Builder matchAllQuery = new MatchAllQuery.Builder();
        return matchAllQuery.build();
    }

    public static Supplier<Query> supplierWithName(String fieldValue) {
        return () -> Query.of(q -> q.match(matchQueryWithNameField(fieldValue)));
    }

    public static MatchQuery matchQueryWithNameField(String fieldValue) {
        MatchQuery.Builder matchQuery = new MatchQuery.Builder();
        return matchQuery.field("name").query(fieldValue).build();
    }


}
