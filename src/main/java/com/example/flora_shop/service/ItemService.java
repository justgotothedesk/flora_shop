package com.example.flora_shop.service;

import com.example.flora_shop.domain.Item;
import com.example.flora_shop.repository.ItemRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Long create(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    public Optional<Item> findByID(Long id) {
        return itemRepository.findById(id);
    }
}
