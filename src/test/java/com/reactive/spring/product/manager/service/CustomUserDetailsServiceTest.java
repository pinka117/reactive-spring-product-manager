package com.reactive.spring.product.manager.service;

import com.reactive.spring.product.manager.model.User;
import com.reactive.spring.product.manager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = CustomUserDetailsService.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsService cudService;

    @Test
    public void testLoadOk() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("password");
        user.addRole("ROLE_ADMIN");
        given(userRepository.findByUsername("admin")).
                willReturn(user
                );
        UserDetails ud = cudService.loadUserByUsername("admin");
        assertEquals("admin", ud.getUsername());
        assertEquals("password", ud.getPassword());
        assertFalse(ud.getAuthorities().isEmpty());

    }

    @Test
    public void testLoadNotFound() {
        given(userRepository.findByUsername("admin")).
                willReturn(null
                );
        UserDetails ud = cudService.loadUserByUsername("admin");
        assertNull(ud);
    }

}
