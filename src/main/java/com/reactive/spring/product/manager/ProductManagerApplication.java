package com.reactive.spring.product.manager;

import com.reactive.spring.product.manager.config.Data;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ProductManagerApplication {


    //Delete db and add some default elements
     @Bean
     public ApplicationRunner initialize(final MongoTemplate mongoTemplate) {
         return args ->
                 Data.initializeAllData(mongoTemplate);
     }

    public static void main(String[] args) {
        SpringApplication.run(ProductManagerApplication.class, args);
    }

}
