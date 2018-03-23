package com.reactive.spring.product.manager.repository;

import com.reactive.spring.product.manager.model.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveMongoRepository<Item, String> {
    public Flux<Item> findByTimestampLessThanEqualOrderByTimestampDesc(final Long timestamp);

    @Tailable
    public Flux<Item> findByTimestampGreaterThan(final Long timestamp);
}
