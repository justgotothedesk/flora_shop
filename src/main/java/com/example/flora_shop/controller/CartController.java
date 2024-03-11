package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.Cart;
import com.example.flora_shop.domain.CartItem;
import com.example.flora_shop.domain.Item;
import com.example.flora_shop.service.CartItemService;
import com.example.flora_shop.service.CartService;
import com.example.flora_shop.service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CartController {
    private final CartService cartService;
    private final HttpSession httpSession;
    private final CartItemService cartItemService;
    private final ItemService itemService;

    public CartController(CartService cartService, HttpSession httpSession, CartItemService cartItemService, ItemService itemService) {
        this.cartService = cartService;
        this.httpSession = httpSession;
        this.cartItemService = cartItemService;
        this.itemService = itemService;
    }

    @GetMapping("/cart")
    public String findCart(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Optional<Cart> temp = cartService.findByUserId(user.getId());
        if (temp.isPresent()) {
            Cart cart = temp.get();
            List<CartItem> cartItems = cartItemService.findByCartID(cart.getId());
            model.addAttribute("cartItems", cartItems);
        }

        return "cart";
    }

    @GetMapping("/cart/remove/{itemId}")
    public String removeCartItem(@PathVariable Long itemId) {
        cartItemService.removeCartItem(itemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/add/{itemId}")
    public String addToCart(@PathVariable Long itemId, @RequestParam int quantity, Model model) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        CartItem cartItem = new CartItem();
        Optional<Item> optionalItem = itemService.findByID(itemId);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            cartItem.setItem(item);
        }
        cartItem.setCount((long) quantity);

        Optional<Cart> optionalCart = cartService.findByUserId(sessionUser.getId());
        if(optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cartItem.setCart(cart);
        }

        cartItemService.create(cartItem);

        model.addAttribute("cartMessage", "장바구니에 상품을 추가했습니다.");

        return "cartResult";
    }
}
