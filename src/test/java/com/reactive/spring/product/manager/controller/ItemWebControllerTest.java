package com.reactive.spring.product.manager.controller;


import com.reactive.spring.product.manager.model.Item;
import com.reactive.spring.product.manager.repository.UserRepository;
import com.reactive.spring.product.manager.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import reactor.core.publisher.Flux;

import java.util.LinkedList;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ItemWebController.class)
public class ItemWebControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    UserRepository userRepository;
    @MockBean
    ItemService it;
    @MockBean
    MongoTemplate mt;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testHomeAdmin() throws Exception {
        Item item = new Item("id1", "name1", "location1");
        LinkedList<Item> li = new LinkedList<>();
        li.add(item);
        Flux<Item> fi = Flux.fromIterable(li);
        given(it.findAll()).
                willReturn(fi);
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testLoginPage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testAdminPageAnonymous() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/admin/index"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testNewPageAdmin() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/new"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testNewPageAnonymous() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/new"))
                .andExpect(status().is3xxRedirection());
    }
}
