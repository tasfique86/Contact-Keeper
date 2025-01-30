package com.scm.scm20.config;


import com.scm.scm20.services.Imp.SecurityCustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //user create and login using java code with in memory service

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user = User
//                .withUsername("admin123")
//                .password("admin123")
//                .roles("ADMIN","USER")
//                .build();
//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user);
//         return inMemoryUserDetailsManager;
//    }

    @Autowired
    private SecurityCustomUserDetailService  userDetailService;

    // configuration for authentication provider spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider  = new DaoAuthenticationProvider();
        //user detail service ki object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        //password encoder ki  object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }


    // configure URL access control to define who can access which endpoints
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity) throws Exception{
        httpsecurity.authorizeHttpRequests(authorize -> {
           // authorize.requestMatchers("/home","/register","services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //form default loginpage
        httpsecurity.formLogin(Customizer.withDefaults());


        return httpsecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
