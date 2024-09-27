# Demo about Elasticsearch integrated with Spring Boot

## Running Elasticsearch in Docker container
https://hub.docker.com/_/elasticsearch?uuid=9BF08200-5218-4C99-9584-7E49DA147AAA
1. With docker CLI commands:
  - docker pull elasticsearch:8.15.2 (docker pull docker.elastic.co/elasticsearch/elasticsearch:8.15.2)
  - docker network create elastic_network
  - docker run --name es01 --net elastic_network -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.15.2

![img_1.png](img_1.png)
![img.png](img.png)

  - docker container ls --all
  - docker container remove <container_id>
  - docker container prune

2. With docker-compose file we benefit:
  - several nodes in a cluster
  - keeping/preserving data entered so far in a volume
  - Kibana GUI for Elasticsearch