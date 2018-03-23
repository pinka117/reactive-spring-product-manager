package com.reactive.spring.product.manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
public class Item {
    @Id
    private String id;
    private String name;
    private String location;
    private Long timestamp = null;

}
