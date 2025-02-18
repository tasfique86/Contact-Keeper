package com.scm.scm20.controller;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.EmailForm;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.EmailService;
import com.scm.scm20.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    // This is for loading user data for every /user/** request
    @ModelAttribute("loggedInUser")
    public void addLoggedInUserInformation(Model model, Authentication authentication){
        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Load user information from addLoggedIn "+username);
        User user=userService.getUserByEmail(username);
        model.addAttribute("loggedInUser",user);
    }

    //user dashboard page using GET method
    @RequestMapping(value = "/dashboard")
    public String userDashboradRequest( ){

        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfileRequest(Model model, Authentication authentication){
        String username= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in : {}",username);

        User user=userService.getUserByEmail(username);
        System.out.println(user.getName());
        System.out.println(user.getEmail());

        model.addAttribute("loggedInUser",user);
        //fetch data from database : email or userName,name.......
        return "user/profile";
    }

//    @RequestMapping("/message")
//    public String addContact(Model model)
//    {
//
//        ContactForm contactForm = new ContactForm();
////        contactForm.setName("Tasfique");
////        contactForm.setEmail("Tasfique@");
////        contactForm.setFavorite(true);
//
//        model.addAttribute("contactForm", contactForm);
//        return "user/message";
//
//    }





}
