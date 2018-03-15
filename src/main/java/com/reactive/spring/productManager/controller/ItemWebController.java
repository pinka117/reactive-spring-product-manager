package com.reactive.spring.productManager.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemWebController {
    @GetMapping("/")
    public String index() {
        return "/user/index";
    }

    @GetMapping("/admin")
    public String index2() {
        return "/admin/index";
    }
}
