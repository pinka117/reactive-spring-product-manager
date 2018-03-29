package com.reactive.spring.product.manager.controller;


import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ItemWebController {
    public static final String MESSAGE = "message";
    public static final String ADMIN_EDIT = "/admin/edit";
    @Autowired
    private ItemService itemService;

    @RequestMapping("/")
    public String index(Model model) {
        Flux<Item> items = itemService.findAll();


        model.addAttribute("items", items.toIterable());

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

    @GetMapping("/new")
    public String newEmployee(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        model.addAttribute(MESSAGE, "");
        return ADMIN_EDIT;
    }

    @PostMapping("/save")
    public String saveItem(Item item, Model model) {
        if (item.getName().equals("")) {
            model.addAttribute("item", item);
            model.addAttribute(MESSAGE, "Empty name");
            return ADMIN_EDIT;
        }
        if (item.getLocation().equals("")) {
            model.addAttribute("item", item);
            model.addAttribute(MESSAGE, "Empty location");
            return ADMIN_EDIT;
        }
        Mono<Item> it = itemService.add(item);
        it.subscribe();
        return "redirect:/";
    }


}
