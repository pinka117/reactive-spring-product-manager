package com.reactive.spring.product.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductManagerApplication {


    /* //Delete db and and some default elements
     @Bean
     public ApplicationRunner initialize(final MongoTemplate mongoTemplate) {
         return args ->
                 Data.initializeAllData(mongoTemplate);
     }
*/
    public static void main(String[] args) {
        SpringApplication.run(ProductManagerApplication.class, args);
    }

}
