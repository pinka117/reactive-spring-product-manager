package com.reactive.spring.product.manager.service;

import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private static int num = 1;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Flux<Item> findAll() {
        return itemRepository.findAll();
    }

    private static synchronized void updateNum() {
        num++;
    }

    public Mono<Item> add(Item item) {
        if (item.getId().equals("")) {
            while (getElement() != null) {
                updateNum();
            }
            item.setId(Integer.toString(num));
        }
        return itemRepository.save(item);
    }

    private Item getElement() {
        return itemRepository.findById(Integer.toString(num)).block();

    }

    public Item search(String id) {
        Mono<Item> it = itemRepository.findById(id);
        if (it == null) return null;
        return itemRepository.findById(id).block();
    }

    public boolean delete(String id) {
        Mono<Item> it = itemRepository.findById(id);
        if (it == null) return false;
        Mono<Void> res = itemRepository.deleteById(id);
        res.subscribe();
        return true;
    }
}
