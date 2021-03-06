package com.reactive.spring.product.manager.security;

import com.reactive.spring.product.manager.model.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyUserDetailsTest {

    @Test
    public void testConfig() {
        User user = new User();
        user.setId("admin");
        user.setUsername("admin");
        user.setPassword("password");
        user.addRole("admin");
        MyUserDetails ud = new MyUserDetails(user);
        assertEquals("admin", ud.getUsername());
        assertEquals("password", ud.getPassword());
        assertTrue(ud.isAccountNonExpired());
        assertTrue(ud.isAccountNonLocked());
        assertTrue(ud.isEnabled());
        assertTrue(ud.isCredentialsNonExpired());
        assertFalse(ud.getAuthorities().isEmpty());
    }
}
