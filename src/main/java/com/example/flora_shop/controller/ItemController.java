package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.*;
import com.example.flora_shop.service.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private final CartService cartService;
    private final CartItemService cartItemService;

    public ItemController(ElasticItemService elasticItemService, ItemService itemService, HttpSession httpSession, UserService userService, OrderService orderService, CartService cartService, CartItemService cartItemService) {
        this.elasticItemService = elasticItemService;
        this.itemService = itemService;
        this.httpSession = httpSession;
        this.userService = userService;
        this.orderService = orderService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
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

    @PostMapping("/purchase")
    public String PurchaseAdd(ItemForm itemForm, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userName", sessionUser.getName());
        }
        Long quantity = itemForm.getQuantity();
        if (quantity == null) {
            quantity = 1L;
        }
        Long itemId = itemForm.getId();

        Optional<Item> optionalItem = itemService.findByID(itemId);
        Optional<User> optionalUser = userService.findByID(sessionUser.getId());

        if (optionalItem.isPresent() && optionalUser.isPresent()) {
            Item item = optionalItem.get();
            User user = optionalUser.get();

            model.addAttribute("item", item);
            model.addAttribute("user", user);
            model.addAttribute("quantity", quantity);

            return "searchAddress";
        }

        return "redirect:/";
    }

    @PostMapping("/purchase/complete")
    public String purchaseComplete(AddressForm addressForm, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userName", sessionUser.getName());
        }
        Long quantity = addressForm.getQuantity();
        Long itemId = addressForm.getItemId();

        Optional<Item> optionalItem = itemService.findByID(itemId);
        Optional<User> optionalUser = userService.findByID(sessionUser.getId());

        if (optionalItem.isPresent() && optionalUser.isPresent()) {
            Item item = optionalItem.get();
            User user = optionalUser.get();

            Order order = new Order();
            order.setUser(user);
            order.setItem(item);
            order.setCount(quantity);
            order.setDetailAddress(addressForm.getDetailAddress());
            order.setRoadAddress(addressForm.getRoadAddress());
            order.setJibunAddress(addressForm.getJibunAddress());
            order.setExtraAddress(addressForm.getExtraAddress());
            order.setPostcode(addressForm.getPostcode());

            orderService.create(order);
            model.addAttribute("order", order);

            return "OrderResult";
        }

        return "redirect:/";
    }

    @PostMapping("/cartadd")
    public String CartAdd(ItemForm itemForm, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("userName", sessionUser.getName());
        }
        Long quantity = itemForm.getQuantity();
        Long itemId = itemForm.getId();

        if (quantity == null) {
            quantity = 1L;
        }

        Optional<Item> optionalItem = itemService.findByID(itemId);
        Optional<Cart> optionalCart = cartService.findByUserId(sessionUser.getId());

        if (optionalItem.isPresent() && optionalCart.isPresent()) {
            Item item = optionalItem.get();
            Cart cart = optionalCart.get();

            Optional<CartItem> optionalCartItem = cartItemService.findByCartIdAndItemId(cart.getId(), itemId);
            if (optionalCartItem.isPresent()) {
                model.addAttribute("message", "장바구니에 존재하는 상품");
                model.addAttribute("item", item);
                return "order";
            }

            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setCount(quantity);
            cartItemService.create(cartItem);
            model.addAttribute("item", item);
        }

        return "order";
    }
}
