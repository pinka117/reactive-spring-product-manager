package com.reactive.spring.product.manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;


@Data
@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String username;
    private String password;
    private LinkedList<String> roles = new LinkedList<String>();

    public void addRole(String role) {
        roles.add(role);
    }

    public String[] getRoleArray() {
        return roles.toArray(new String[roles.size()]);
    }
}
