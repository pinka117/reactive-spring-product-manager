package com.reactive.spring.product.manager.repository;

import com.reactive.spring.product.manager.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {

}
