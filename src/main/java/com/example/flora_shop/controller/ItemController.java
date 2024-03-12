package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.*;
import com.example.flora_shop.service.ElasticItemService;
import com.example.flora_shop.service.ItemService;
import com.example.flora_shop.service.OrderService;
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
    private final OrderService orderService;

    public ItemController(ElasticItemService elasticItemService, ItemService itemService, HttpSession httpSession, UserService userService, OrderService orderService) {
        this.elasticItemService = elasticItemService;
        this.itemService = itemService;
        this.httpSession = httpSession;
        this.userService = userService;
        this.orderService = orderService;
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
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        Optional<Item> optionalItem = itemService.findByID(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            model.addAttribute("item", item);

            return "order";
        }

        return "redirect:/";
    }

    @PostMapping("/a")
    public String temp() {


        return "redirect:/";
    }

    @PostMapping("/b")
    public String temp2() {
        return "redirect:/";
    }

    @PostMapping("/purchase")
    public String create(ItemForm itemForm) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        System.out.println(itemForm.getQuantity());
        System.out.println(itemForm.getId());

        return "redirect:/";
    }

    @PostMapping("/item/purchase")
    public String purchaseItem(@RequestParam Long itemId, @RequestParam int quantity, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }
        return "redirect:/";

//        Optional<Item> optionalItem = itemService.findByID(itemId);
//        Optional<User> optionalUser = userService.findByID(sessionUser.getId());
//
//        if (optionalItem.isPresent() && optionalUser.isPresent()) {
//            Item item = optionalItem.get();
//            User user = optionalUser.get();
//            Order order = new Order();
//            order.setItem(item);
//            order.setUser(user);
//            order.setCount((long) quantity);
//            orderService.create(order);
//
//            model.addAttribute("order", order);
//        }
//
//        return "OrderResult";
    }
}
