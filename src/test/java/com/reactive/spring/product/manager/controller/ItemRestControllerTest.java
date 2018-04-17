package com.reactive.spring.product.manager.controller;

import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import reactor.core.publisher.Flux;

import java.util.LinkedList;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.Mockito.*;

@WithMockUser(username = "admin", roles = {"ADMIN"})
public class ItemRestControllerTest {

    private ItemService itemService;
    private Flux<Item> fi;
    private Flux<Item> emptyFlux;
    private Item item;

    @Before
    public void setUp() {
        itemService = mock(ItemService.class);
        standaloneSetup(new ItemRestController(itemService));
        this.item = new Item("id1", "name1", "location1");
        Item item2 = new Item("id2", "name2", "location2");
        LinkedList<Item> li = new LinkedList<>();
        li.add(item);
        li.add(item2);
        this.fi = Flux.fromIterable(li);
        this.emptyFlux = Flux.empty();
    }

    @Test
    public void findAllTest() throws Exception {
        when(itemService.findAll()).
                thenReturn(fi);
        given().
                when()
                .async().get("/api/items").
                then().
                statusCode(200).
                assertThat().
                body(
                        "id[0]", equalTo("id1"),
                        "name[0]", equalTo("name1"),
                        "location[0]", equalTo("location1"),
                        "id[1]", equalTo("id2"),
                        "name[1]", equalTo("name2"),
                        "location[1]", equalTo("location2")
                );
        verify(itemService, times(1)).findAll();
    }

    @Test
    public void testFindByIdOk() throws Exception {
        when(itemService.search("id1")).
                thenReturn(item);
        given().
                when().
                get("/api/item/id1").
                then().
                statusCode(200).
                assertThat().
                body(
                        "id", equalTo("id1"),
                        "name", equalTo("name1"),
                        "location", equalTo("location1")
                );
        verify(itemService, times(1)).search("id1");
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        given().
                when().
                get("/api/item/id2").
                then().
                statusCode(200).
                contentType(isEmptyOrNullString());
        verify(itemService, times(1)).search("id2");
    }

    @Test
    public void testAdd() throws Exception {
        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(item).
                when().
                post("/api/items/new").
                then().
                statusCode(200);
        verify(itemService, times(1)).add(item);
    }

    @Test
    public void testModifyOk() throws Exception {
        when(itemService.search("id1")).
                thenReturn(item);
        Item mod = new Item("id1", "new name", "new location");

        given().
                contentType(MediaType.APPLICATION_JSON_VALUE).
                body(mod).
                when().
                put("/api/item/modify/id1").
                then().
                statusCode(200);

        verify(itemService, times(1)).add(mod);
    }

    @Test
    public void testDelete() throws Exception {
        given().
                when().
                delete("/api/items/delete/id1").
                then().
                statusCode(200);
        verify(itemService, times(1)).delete("id1");
    }
}



