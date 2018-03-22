package com.reactive.spring.product.manager;

import com.reactive.spring.product.manager.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Data {
    public static void initializeAllData(MongoTemplate mongoTemplate) {

        User user = new User();
        user.setId("admin");
        user.setUsername("admin");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("password"));
        user.addRole("admin");
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.createCollection(User.class);
        mongoTemplate.insert(user);
    }
}
