package com.demo.elasticsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;

import java.util.ArrayList;
import java.util.List;
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

    public static Supplier<Query> supplierBool(String productNameValue, Integer quantity) {
        return () -> Query.of(q -> q.bool(boolQuery(productNameValue,quantity)));
    }

    public static BoolQuery boolQuery(String productNameValue, Integer quantity) {
        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        return boolQuery.filter(termQuery(productNameValue)).must(matchQuery(quantity)).build();
    }

    public static List<Query> termQuery(String productNameValue) {
        List<Query> terms = new ArrayList<>();
        TermQuery.Builder termQuery = new TermQuery.Builder();

        terms.add(Query.of(q -> q.term(termQuery.field("name").value(productNameValue).build())));

        return terms;
    }

    public static List<Query> matchQuery(Integer quantity) {
        List<Query> matches = new ArrayList<>();
        MatchQuery.Builder matchQuery = new MatchQuery.Builder();

        matches.add(Query.of(q -> q.match(matchQuery.field("quantity").query(quantity).build())));

        return matches;
    }
}
