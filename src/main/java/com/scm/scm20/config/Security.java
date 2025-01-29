package com.scm.scm20.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class Security {

    //user create and login using java code with in memory service

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails user = User
                .withUsername("admin123")
                .password("admin123")
                .roles("ADMIN","USER")
                .build();
        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
         return inMemoryUserDetailsManager;
    }

}
