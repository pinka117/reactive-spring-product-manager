package com.reactive.spring.product.manager.service;

import com.reactive.spring.product.manager.model.User;
import com.reactive.spring.product.manager.repository.UserRepository;
import com.reactive.spring.product.manager.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@ComponentScan(basePackages = {"com.reactive.spring.product.manager"})
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return new MyUserDetails(user);
    }
}
