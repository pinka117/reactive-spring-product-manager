package com.reactive.spring.product.manager.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemWebController {

    @RequestMapping("/")
    public String index() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "/admin/index";

        }
        return "/user/index";
    }

    @RequestMapping(value = "/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return "alreadyLoggedIn";
        }

        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "login";
    }

}
