package com.reactive.spring.product.manager.controller;


import com.reactive.spring.product.manager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemWebControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    UserRepository userRepository;

    @Test
    @WithAnonymousUser
    public void testHomeAnonymous() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testHomeAdmin() throws Exception {
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
