package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.Item;
import com.example.flora_shop.domain.Role;
import com.example.flora_shop.domain.User;
import com.example.flora_shop.service.ItemService;
import com.example.flora_shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    private final HttpSession httpSession;
    private final UserService userService;
    private final ItemService itemService;

    public HomeController(HttpSession httpSession, UserService userService, ItemService itemService) {
        this.httpSession = httpSession;
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/")
    public String home(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        List<Item> items = itemService.findAll();
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        if (items != null) {
            model.addAttribute("items", items);
        }

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOptional = userService.findByIDandPassword(username, password);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            httpSession.setAttribute("user", new SessionUser(user));
            return "redirect:/";
        } else {
            return "redirect:/login?error";
        }
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String create(UserForm userForm) {
        User user = new User(userForm.getName(), "null", "null", Role.USER, userForm.getUsername(), userForm.getPassword());
        userService.create(user);

        return "redirect:/";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "upload";
    }

    @GetMapping("/mypage")
    public String mypage() {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        return "mypage";
    }
}
