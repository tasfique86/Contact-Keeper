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
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
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

        // identify the provider

        var aouthToken= (OAuth2AuthenticationToken)authentication;
        String authorizedClientRegistrationId= aouthToken.getAuthorizedClientRegistrationId();

        var oauthUser= (DefaultOAuth2User)authentication.getPrincipal();

//        oauthUser.getAttributes().forEach((k, v) -> {
//            logger.info(k + " :-> " + v);
//        });

        User userSave=new User();
    //    userSave.setEmail(oauthUser.getAttribute("email").toString());
        userSave.setUserId(UUID.randomUUID().toString());
        userSave.setRoleList(List.of(AppConstents.ROLE_USER));
        userSave.setEmailVerified(true);
        userSave.setEnabled(true);

         logger.info(authorizedClientRegistrationId);

         if(authorizedClientRegistrationId.equals("google")){

            userSave.setName(oauthUser.getAttribute("name").toString());
            userSave.setProfilePic(oauthUser.getAttribute("picture").toString());
            userSave.setProviders(Providers.GOOGLE);
            userSave.setProviderUserId(oauthUser.getName());
            userSave.setAbout("This account is created by google");
            userSave.setPassword("password");
          //  userRepositories.save(userSave);
            //logger.info("User saved "+userSave.getEmail());
         }
         else if(authorizedClientRegistrationId.equals("github")){

             String email=oauthUser.getAttribute("email") != null ?
                     oauthUser.getAttribute("email").toString() :  oauthUser.getAttribute("login").toString()+"@github.com";
             String name=oauthUser.getAttribute("login").toString();
             String profilePic=oauthUser.getAttribute("avatar_url").toString();
             String providerUserId=oauthUser.getName();

             userSave.setName(name);
             userSave.setProfilePic(profilePic);
             userSave.setProviders(Providers.GITHUB);
             userSave.setProviderUserId(providerUserId);
             userSave.setEmail(email);
             userSave.setAbout("This account is created by github");
             userSave.setPassword("password");
         }
         else if(authorizedClientRegistrationId.equals("linkedin")){

         }
         else {
            logger.info("OAuthAuthenticationSuccessHandler : Unknown provider");
         }


//------  Save the OAuth user details in the database if the OAuth user does not exist in the database.

         User findDataBaseUser=userRepositories.findByEmail(userSave.getEmail()).orElse(null);

         if(findDataBaseUser==null){
             userRepositories.save(userSave);
             logger.info("User saved "+userSave.getEmail());
         }

        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");


//       DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();
//
////       logger.info(user.getName());
////        user.getAttributes().forEach((k, v) -> {
////            logger.info(k + " :-> " + v);
////        });
//
//        User savedUser=new User();
//        savedUser.setEmail(user.getAttribute("email").toString());
//        savedUser.setName(user.getAttribute("name").toString());
//        savedUser.setProfilePic(user.getAttribute("picture").toString());
//        savedUser.setPassword("password");
//
//        savedUser.setUserId(UUID.randomUUID().toString());
//        savedUser.setProviders(Providers.GOOGLE);
//        savedUser.setEnabled(true);
//        savedUser.setEmailVerified(true);
//        savedUser.setRoleList(List.of(AppConstents.ROLE_USER));
//
//        savedUser.setAbout("This account is created by google");
//
////-------------   fatch data from database to check this is user exist or not
//        User databaseUser=userRepositories.findByEmail(savedUser.getEmail()).orElse(null);
//
//        if(databaseUser==null){
//            userRepositories.save(savedUser);
//            logger.info("User saved "+savedUser.getEmail());
//        }
//        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");



    }
}
