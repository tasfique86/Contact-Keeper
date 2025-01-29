package com.scm.scm20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {

    //user dashboard page using GET method
    @RequestMapping(value = "/dashboard")
    public String userDashboradRequest( ){

        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String userProfileRequest( ){
        return "user/profile";
    }
}
