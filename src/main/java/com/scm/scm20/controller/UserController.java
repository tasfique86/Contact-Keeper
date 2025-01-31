package com.scm.scm20.controller;

import com.scm.scm20.helper.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    //user dashboard page using GET method
    @RequestMapping(value = "/dashboard")
    public String userDashboradRequest( ){

        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfileRequest(Authentication authentication){
        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in : {}",username);

        //fetch data from database : email or userName,name.......
        return "user/profile";
    }
}
