package com.reactive.spring.product.manager.config;

import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Data {

    public static final String ADMIN = "admin";

    private Data() {

    }

    public static void initializeAllData(MongoTemplate mongoTemplate) {

        User user = new User();
        user.setId(ADMIN);
        user.setUsername(ADMIN);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("admin"));
        user.addRole(ADMIN);
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.createCollection(User.class);
        mongoTemplate.insert(user);
        mongoTemplate.dropCollection(Item.class);
        mongoTemplate.createCollection(Item.class);
        Item it = new Item("id1", "name1", "location1");
        mongoTemplate.insert(it);



    }
}
