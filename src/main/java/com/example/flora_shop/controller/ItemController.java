package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.ElasticItem;
import com.example.flora_shop.domain.Item;
import com.example.flora_shop.domain.User;
import com.example.flora_shop.service.ElasticItemService;
import com.example.flora_shop.service.ItemService;
import com.example.flora_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ItemController {
    private final ElasticItemService elasticItemService;
    private final ItemService itemService;
    private final HttpSession httpSession;
    private final UserService userService;

    public ItemController(ElasticItemService elasticItemService, ItemService itemService, HttpSession httpSession, UserService userService) {
        this.elasticItemService = elasticItemService;
        this.itemService = itemService;
        this.httpSession = httpSession;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam String name, @RequestParam Long price, @RequestParam String description) {
        Item item = new Item();
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        Optional<User> temp = userService.findByID(sessionUser.getId());
        if (temp.isPresent()) {
            User user = temp.get();
            item.setUser(user);
        }
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

    @GetMapping("/item/{itemId}")
    public String showPurchaseItemPage(@PathVariable Long itemId, Model model) {
        Optional<Item> optionalItem = itemService.findByID(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            model.addAttribute("item", item);

            return "order";
        }

        return "redirect:/";
    }

    @PostMapping("/item/purchase/{itemId}")
    public String purchaseItem(@PathVariable Long itemId, @RequestParam int quantity, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect/login";
        }

        model.addAttribute("purchaseMessage", "상품을 구매했습니다.");

        return "purchaseResult";
    }
}
