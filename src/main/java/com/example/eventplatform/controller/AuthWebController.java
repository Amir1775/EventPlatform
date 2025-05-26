package com.example.eventplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthWebController {

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login"; // Предполагается, что у тебя есть login.html
//    }
}