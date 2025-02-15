package com.scm.scm20.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class Helper {


    public static String getEmailOfLoggedInUser(Authentication authentication) {
        Logger logger = LoggerFactory.getLogger(Helper.class);

        //--This section is for logging in using OAuth2, such as Google, GitHub, and LinkedIn.
        if(authentication instanceof OAuth2AuthenticationToken){

            var aOAuth2AuthenticationToken= (OAuth2AuthenticationToken)authentication;
            var clientId=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2User=(OAuth2User)authentication.getPrincipal();
            String userName="";
            if(clientId.equalsIgnoreCase("google")){

                userName= oauth2User.getAttributes().get("email").toString();
                 logger.info("Getting from  : "+clientId);
               // System.out.println("From Google");
            } else if (clientId.equalsIgnoreCase("github")) {
                  userName=oauth2User.getAttribute("email") != null ?
                        oauth2User.getAttribute("email").toString() :  oauth2User.getAttribute("login").toString()+"@github.com";
                logger.info("Getting from : "+clientId );

            }
            return  userName;

        }
        else {
            logger.info("Getting from local Database");
            return authentication.getName();
        }

    }

    public static String getLinkForEmailVerification(String emailTokem) {
        String link="http://localhost:8080/auth/verify-email?token="+emailTokem;
        return link;
    }
}
