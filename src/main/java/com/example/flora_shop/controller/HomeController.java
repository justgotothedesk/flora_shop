package com.example.flora_shop.controller;

import com.example.flora_shop.config.oauth.SessionUser;
import com.example.flora_shop.domain.Normal_User;
import com.example.flora_shop.service.NormalUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class HomeController {
    private final HttpSession httpSession;
    private final NormalUserService normalUserService;

    public HomeController(HttpSession httpSession, NormalUserService normalUserService) {
        this.httpSession = httpSession;
        this.normalUserService = normalUserService;
    }

    @GetMapping("/")
    public String home(Model model) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        Normal_User nuser = (Normal_User) httpSession.getAttribute("nuser");
        if (user != null) {
            model.addAttribute("userName", user.getName());
        } else if (nuser != null) {
            model.addAttribute("userName", nuser.getName());
        }

        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Optional<Normal_User> userOptional = normalUserService.findbyIDandPassword(username, password);

        if(userOptional.isPresent()) {
            Normal_User normal_user = userOptional.get();
            httpSession.setAttribute("nuser", normal_user);
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
        Normal_User normal_user = new Normal_User();
        normal_user.setName(userForm.getName());
        normal_user.setUsername(userForm.getUsername());
        normal_user.setPassword(userForm.getPassword());
        normalUserService.create(normal_user);

        return "redirect:/";
    }
}
