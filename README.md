# Demo about Elasticsearch integrated with Spring Boot
https://spring.io/projects/spring-data-elasticsearch#learn


### Running Elasticsearch in Docker container
https://hub.docker.com/_/elasticsearch?uuid=9BF08200-5218-4C99-9584-7E49DA147AAA

https://www.elastic.co/guide/en/elasticsearch/reference/8.15/docker.html

**I. With docker CLI commands:**
1. Elasticsearch engine starting **without security**
  - docker pull elasticsearch:8.15.2 (docker pull docker.elastic.co/elasticsearch/elasticsearch:8.15.2)
  - docker network create elastic_network
  - docker run --name es01 --net elastic_network -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.15.2

In below photo, `cluster_name` is `docker-cluster`:


![img_1.png](img_1.png)


After running for the first time our Spring application, the name of our document (table) is `product_index`:


![img.png](img.png)

  - docker container ls --all
  - docker container remove <container_id>
  - docker container prune
  - docker network ls
  - docker network rm <network_name>

2. Kibana GUI for Elasticsearch starting **without security**
  - docker pull docker.elastic.co/kibana/kibana:8.15.2
  - docker run --name kib01 --net elastic_network -p 5601:5601 -e "ELASTICSEARCH_HOSTS=http://es01:9200" docker.elastic.co/kibana/kibana:8.15.2
  - GUI is available on localhost:5601, and **dev tools** are on http://localhost:5601/app/dev_tools#/console

![img_3.png](img_3.png)



3. Elasticsearch engine starting **with security** enabled:
- docker run --name es01 --net elastic_network -p 9200:9200 -it -e "discovery.type=single-node" -e "xpack.security.enrollment.enabled=true" docker.elastic.co/elasticsearch/elasticsearch:8.15.2

![img_2.png](img_2.png)




**II. With docker-compose file:**
1. One node cluster and Kibana
  - run `docker-compose up` to load the **docker-compose.yaml** file
  - run `docker-compose down` to preserve data entered so far
  - run `docker-compose down --volumes` to delete also the volume and the data entered so far 

2. Three node cluster and Kibana
  - run `docker-compose -f docker-compose-3Node-cluster.yaml up` to load the **docker-compose-3Node-cluster.yaml**
  - run `docker-compose -f docker-compose-3Node-cluster.yaml down` to preserve data entered so far
  - run `docker-compose -f docker-compose-3Node-cluster.yaml down --volumes` to delete all 3 volumes and erase the data entered so far



### Test all the CRUD operations
Using POSTMAN and the resource http://localhost:8080/api/products


### Elasticsearch integrated search query API - based on Elasticsearch DSL (mostly JSON DSL)
https://www.elastic.co/guide/en/elasticsearch/reference/current/search-your-data.html

You can run this resource http://localhost:9200/product_index/_search on the browser to see the current available records for index `product_index`.

In case you want to search among all the tables (documents/indexes), then use http://localhost:9200/_search

If you run this resource http://localhost:9200/product_index/_search from Postman (allowed: [GET, POST]), 
you can specify specific search options in the body request:
- `{"query": {"match": {"id": 101}}}` - match query
- `{"query": {"wildcard": {"name": "m*"}}}` - searching caseInsensitive where `name` begins with `m`
- `{"query": {"match_all": {}}}` - match_all query
- `{"query": {"bool": {"filter": [{"term": {"name": "tablet"}}], "must": [{"match": {"quantity": 2}}]}}}` - bool query - https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html
- `{"query": {"multi_match": {"query": "GSM", "fields": ["name", "nameDesc"]}}}` - match_all query - https://www.elastic.co/guide/en/elasticsearch/guide/current/multi-match-query.html


### Elasticsearch DSL - create index and create document query API
- POST query - http://localhost:9200/users/_doc/103 with body: `{"userId": 103, "name": "Svilen", "age": 39}` - created index `users` with a document record `{"userId": 103, "name": "Svilen", "age": 39}`


### Implementing Elasticsearch DSL search API with Java
- run GET on `localhost:8080/api/matchall` - searches in all indexes (documents/tables)
- run GET on `localhost:8080/api/products/matchallproducts` - searches in index `product_index`
- run GET on `localhost:8080/api/products/matchallproducts/{fieldValue}` - searches in index `product_index` with that fieldValue
- run GET on `localhost:8080/api/products/boolquery/{productNameValue}/{quantity}` - searches in index `product_index` with bool combined query


### More configuration of Elasticsearch
1. Elasticsearch Clients - **Imperative Rest Client**(ElasticsearchConfiguration) or **Reactive Rest Client**
2. We could use **Elasticsearch Object Mapping** - registering converters, using annotations for mapping such as @Field, using Elasticsearch Query Builder, etc - https://docs.spring.io/spring-data/elasticsearch/reference/elasticsearch/object-mapping.html
3. We could use blocking **Elasticsearch Operations** 
- **IndexOperations** defines actions on index level like **creating or deleting an index**.
- **DocumentOperations** defines actions to store, update and retrieve entities based on their id.
- **SearchOperations** define the actions to search for multiple entities using queries
- **ElasticsearchOperations** combines the DocumentOperations and SearchOperations interfaces.

Basically one should just use the ElasticsearchOperations to interact with the Elasticsearch cluster. When using repositories, this instance is used under the hood as well.

The **ElasticsearchTemplate** (which inherits ElasticsearchOperations) is a bit of old approach. We could directly inject the ElasticsearchOperations. 

4. We could also use non-blocking **Reactive Elasticsearch Operations** 