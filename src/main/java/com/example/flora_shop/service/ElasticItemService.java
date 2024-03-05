package com.example.flora_shop.service;

import com.example.flora_shop.domain.ElasticItem;
import com.example.flora_shop.repository.ElasticItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ElasticItemService {
    private final ElasticItemRepository elasticItemRepository;

    public ElasticItemService(ElasticItemRepository elasticItemRepository) {
        this.elasticItemRepository = elasticItemRepository;
    }

    public Long create(ElasticItem elasticItem) {
        elasticItemRepository.save(elasticItem);
        return elasticItem.getId();
    }

    public Optional<ElasticItem> findByID(Long id) {
        return elasticItemRepository.findById(id);
    }
}
