package com.reactive.spring.product.manager.security;

import com.reactive.spring.product.manager.model.Item;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SecurityRestIT {
    @Autowired
    private WebApplicationContext context;
    private Item item;

    @Before
    public void setUp() {
        this.item = new Item("id1", "name1", "location1");
    }

    @Test
    @WithAnonymousUser
    public void testItemsAnonymous() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                when().
                get("/api/items").
                then().
                statusCode(200);
    }

    @Test
    @WithAnonymousUser
    public void testNewAnonymous() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(item).
                when().
                post("/api/items/new").
                then().
                statusCode(302);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testNewAdmin() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(item).
                when().
                post("/api/items/new").
                then().
                statusCode(200);
    }

    @Test
    @WithAnonymousUser
    public void testModifyAnonymous() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(item).
                when().
                put("/api/item/modify/id1").
                then().
                statusCode(302);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testModifyAdmin() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(item).
                when().
                put("/api/item/modify/id1").
                then().
                statusCode(200);
    }

    @Test
    @WithAnonymousUser
    public void testDeleteAnonymous() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                when().
                delete("/api/items/delete/id1").
                then().
                statusCode(302);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteAdmin() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                when().
                delete("/api/items/delete/id1").
                then().
                statusCode(200);
    }

    @Test
    @WithAnonymousUser
    public void testFindAnonymous() {
        RestAssuredMockMvc.given().
                webAppContextSetup(context).
                when().
                get("/api/item/id2").
                then().
                statusCode(200);
    }
}
