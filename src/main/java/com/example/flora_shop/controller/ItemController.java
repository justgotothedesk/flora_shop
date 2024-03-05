package com.example.flora_shop.controller;

import com.example.flora_shop.domain.ElasticItem;
import com.example.flora_shop.domain.Item;
import com.example.flora_shop.service.ElasticItemService;
import com.example.flora_shop.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final ElasticItemService elasticItemService;
    private final ItemService itemService;

    public ItemController(ElasticItemService elasticItemService, ItemService itemService) {
        this.elasticItemService = elasticItemService;
        this.itemService = itemService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam String name, @RequestParam Long price, @RequestParam String description) {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setDescription(description);
        itemService.create(item);

        ElasticItem elasticItem = new ElasticItem();
        elasticItem.setName(name);
        elasticItem.setPrice(price);
        elasticItemService.create(elasticItem);

        return "redirect:/";
    }
}
