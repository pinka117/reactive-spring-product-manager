package com.reactive.spring.product.manager.controller;

import com.reactive.spring.product.manager.model.DTOItem;
import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ItemRestController {
    private ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public Flux<Item> findAll() {
        return itemService.findAll();
    }

    @GetMapping("/item/{id}")
    public Item search(@PathVariable String id) {
        return itemService.search(id);
    }

    @PostMapping("/items/new")
    public Mono<Item> newItem(@RequestBody DTOItem itemDto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(itemDto, Item.class);
        return itemService.add(item);
    }

    @PutMapping("/item/modify/{id}")
    public Mono<Item> modifyItem(@PathVariable String id, @RequestBody DTOItem itemDto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(itemDto, Item.class);
        item.setId(id);
        return itemService.add(item);
    }

    @DeleteMapping("/items/delete/{id}")
    public void delete(@PathVariable String id) {
        itemService.delete(id);
    }

}