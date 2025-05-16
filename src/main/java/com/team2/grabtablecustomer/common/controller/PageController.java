package com.team2.grabtablecustomer.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String index() {
        return "/index.html";
    }

    @GetMapping("/register")
    public String register() {
        return "/register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/login.html";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/index.html";
    }

    @GetMapping("/board")
    public String board() {
        return "/board.html";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "/mypage.html";
    }

    @GetMapping("/customer-info")
    public String customerInfo() {
        return "/customer-info.html";
    }
  
    @GetMapping("/store-detail")
    public String storeDetail() {
        return "/store-detail.html";
    }
  
    @GetMapping("/category")
    public String category() {
        return "/category.html";
    }
}
