package com.team2.grabtablecustomer.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/register")
    public String register() {
        return "/register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "/login.html";
    }

    @GetMapping("/board")
    public String board() {
        return "/board.html";
    }

    @GetMapping("/reservation")
    public String reservation() {
        return "/reservation.html";
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
