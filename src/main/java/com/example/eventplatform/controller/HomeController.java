package com.example.eventplatform.controller;

import com.example.eventplatform.repository.User;
import com.example.eventplatform.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        User currentUser = userService.getCurrentUser();
        if (currentUser != null) {
            model.addAttribute("user", currentUser);
            model.addAttribute("userName", currentUser.getName()); // Добавляем имя
            System.out.println("User name added to model: " + currentUser.getName()); // Для отладки
        } else {
            model.addAttribute("user", new User());
            model.addAttribute("userName", "Guest");
            System.out.println("No user found, added Guest to model");
        }
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("user", currentUser);
        return "profile";
    }

    }


