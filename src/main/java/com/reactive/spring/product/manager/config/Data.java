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

        if (!mongoTemplate.collectionExists(User.class)) {
            mongoTemplate.createCollection(User.class);
        }
        if (!mongoTemplate.collectionExists(Item.class)) {
            mongoTemplate.createCollection(Item.class);
        }
        if (mongoTemplate.findById("admin", User.class) == null) {
            User user = new User();
            user.setId(ADMIN);
            user.setUsername(ADMIN);
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(ADMIN));
            user.addRole("ROLE_ADMIN");
            mongoTemplate.insert(user);
        }
    }
}
