package com.scm.scm20.controller;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    // This is for loading user data for every /user/** request
    @ModelAttribute("loggedInUser")
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        if(authentication==null){
            return;
        }
        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Load user information from addLoggedIn "+username);

        User user=userService.getUserByEmail(username);

            logger.info("LoggedIn user: "+username);
            logger.info("LoggedIn user: "+ user.getName());
            model.addAttribute("loggedInUser",user);
    }
}
