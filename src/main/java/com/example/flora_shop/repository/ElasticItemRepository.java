package com.example.flora_shop.repository;

import com.example.flora_shop.domain.ElasticItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticItemRepository extends ElasticsearchRepository<ElasticItem, Long> {
}
