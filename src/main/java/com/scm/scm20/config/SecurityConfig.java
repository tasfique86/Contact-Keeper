package com.scm.scm20.config;


import com.scm.scm20.services.Imp.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

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

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
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

        //-------------------------login page handel-------------------------------------
        httpsecurity.formLogin(formLogin ->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
//            formLogin.failureForwardUrl("/login?error=true");

            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

//            formLogin.failureHandler(new AuthenticationFailureHandler() {
//                @Override
//                public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                    throw new UnsupportedEncodingException("");
//                }
//            });

//            formLogin.successHandler(new AuthenticationSuccessHandler() {
//                @Override
//                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//                }
//            });


        });

        //-----------logout Handel-------------
        httpsecurity.csrf(AbstractHttpConfigurer::disable);
        httpsecurity.logout(logout ->{
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");

        });


        //----------here oauth configuration
        httpsecurity.oauth2Login(oauth ->{
            oauth.loginPage("/login");
            oauth.successHandler(authenticationSuccessHandler);
        });


        return httpsecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
