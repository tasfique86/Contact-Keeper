package com.scm.scm20.controller;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.repositories.UserRepositories;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepositories userRepositories;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam ("token") String token, HttpSession session) {

//        System.out.println("Email token: " + token);
        User user= userRepositories.findByEmailToken(token).orElse(null);

        if(user!=null) {

            if(user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepositories.save(user);
                session.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("Successfully verified your email")
                        .build()
                );
                return "success_page";
            }

        }
        session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email is not verified ! Something went wrong !!")
                .build()
        );
        return "error_page";
    }

}
