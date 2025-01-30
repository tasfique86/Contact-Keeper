package com.scm.scm20.config;

import com.scm.scm20.entities.Providers;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstents;
import com.scm.scm20.repositories.UserRepositories;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class OAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger= LoggerFactory.getLogger(OAuthenticationSuccessHandler.class);
    @Autowired
    private UserRepositories userRepositories;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        logger.info("onAuthenticationSuccessHandler");

       DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();

//       logger.info(user.getName());
//        user.getAttributes().forEach((k, v) -> {
//            logger.info(k + " :-> " + v);
//        });

        User savedUser=new User();
        savedUser.setEmail(user.getAttribute("email").toString());
        savedUser.setName(user.getAttribute("name").toString());
        savedUser.setProfilePic(user.getAttribute("picture").toString());
        savedUser.setPassword("password");

        savedUser.setUserId(UUID.randomUUID().toString());
        savedUser.setProviders(Providers.GOOGLE);
        savedUser.setEnabled(true);
        savedUser.setEmailVerified(true);
        savedUser.setRoleList(List.of(AppConstents.ROLE_USER));

        savedUser.setAbout("This account is created by google");

//-------------   fatch data from database to check this is user exist or not
        User databaseUser=userRepositories.findByEmail(savedUser.getEmail()).orElse(null);

        if(databaseUser==null){
            userRepositories.save(savedUser);
            logger.info("User saved "+savedUser.getEmail());
        }


        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
    }
}
