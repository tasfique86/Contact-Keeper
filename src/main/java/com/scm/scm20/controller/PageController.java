package com.scm.scm20.controller;

import com.scm.scm20.entities.User;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.aspectj.weaver.patterns.BindingPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.lang.String;


@Controller
public class PageController {


     @Autowired
     private UserService userService;

     @GetMapping("/")
     public String index( ) {
         return "redirect:/home";
     }

    @RequestMapping("/home")
    public String Home( ) {

        return "home";
    }
    @RequestMapping("/about")
    public String About( ) {

        return "about";
    }


    @GetMapping("/services")
    public String Service() {

        return "services";
    }

    @GetMapping("/register")
    public String Register(Model model) {
        UserForm userForm = new UserForm();
      //  userForm.setName(" ");
       //userForm.setName("Tasfique");
        model.addAttribute("userForm", userForm);

        return "register";
    }

    @GetMapping("/contact")
    public String Contact() {

        return "contact";
    }

    @GetMapping("/login")
    public String Login() {

        return new String("login");
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session) {
        System.out.println(userForm);

        //-----validation-----------------

        if(bindingResult.hasErrors() ){
            return "register";
        }


        // userForm to user convert using builder (---- way 1 -----------)
//        User user = User.builder()
//                .name(userForm.getName())
//                .email(userForm.getEmail())
//                .about(userForm.getAbout())
//                .password(userForm.getPassword())
//                .phoneNumber(userForm.getPhoneNumber())
//                .profilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1738071894~exp=1738075494~hmac=e00af52fe6f2c2bc9cee05581407f4b21ce17958d780f890570b23ebe750ef8e&w=826")
//                .build();


        // userForm to user convert (--------- way 2 ----------)

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setPassword(userForm.getPassword());
        user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1738071894~exp=1738075494~hmac=e00af52fe6f2c2bc9cee05581407f4b21ce17958d780f890570b23ebe750ef8e&w=826");


        User saveUser= userService.saveUser(user);

        System.out.println("save successfully");
        // add message
        Message message = Message.builder().content("Registration successful").type(MessageType.green).build();
        session.setAttribute("message",message);



        return "redirect:/register";

    }
}
