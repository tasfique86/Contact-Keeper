package com.scm.scm20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//check
//check 2
@Controller
public class PageController {

    @RequestMapping("/home")
    public String Home(Model model) {

        return "home";
    }
    @RequestMapping("/about")
    public String About(Model model) {

        return "about";
    }


    @GetMapping("/services")
    public String Service() {

        return "services";
    }

    @GetMapping("/register")
    public String Register() {

        return "register";
    }

    @GetMapping("/contact")
    public String Contact() {

        return "contact";
    }

    @GetMapping("/login")
    public String Login() {

        return "login";
    }
}
