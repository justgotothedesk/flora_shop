package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.CartItem;
import com.example.flora_shop.service.CartItemService;
import com.example.flora_shop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    private HttpSession httpSession;
    private CartItemService cartItemService;

    @GetMapping("/")
    public String findCart(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        Long temp = cartService.findByUserId(user.getId());
        if (temp != null) {
            List<CartItem> cartItems = cartItemService.findByCartID(temp);
            model.addAttribute("cartItems", cartItems);
        }

        return "cart";
    }
}
