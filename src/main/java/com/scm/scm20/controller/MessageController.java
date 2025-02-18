package com.scm.scm20.controller;


import com.scm.scm20.forms.EmailForm;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.EmailService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("user/email")
public class MessageController {

    @Autowired
    private EmailService emailService;

    Logger logger = LoggerFactory.getLogger(MessageController.class);


    @RequestMapping("/send")
    public String email( Model model){

        EmailForm emailForm = new EmailForm();
        model.addAttribute("emailForm", emailForm);
        return "user/message";
    }

    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public String sendMail(@Valid
                               @ModelAttribute EmailForm  emailForm,
                           BindingResult bindingResult,
                           Authentication authentication,
                           HttpSession httpSession)
    {

        if(bindingResult.hasErrors()){
            httpSession.setAttribute("messsage", Message.builder()
                    .content("Please enter a valid email address")
                    .type(MessageType.red)
                    .build());
            return "user/message";
        }
        logger.info(emailForm.getTo());
        logger.info(emailForm.getSubject());
        logger.info(emailForm.getBody());

        //here userName ==  user email
        String username = Helper.getEmailOfLoggedInUser(authentication);

       // logger.info("emailForm: " + emailForm.toString()+" "+emailForm.getSubject());

        // just testing purpose  --- it for clarity. who actually send mail
        String subject = "Send by : "+username+" || "+emailForm.getSubject();

        emailService.sendEmail(emailForm.getTo(), subject, emailForm.getBody());



        httpSession.setAttribute("message", Message.builder()
                .content("Your contact has been saved successfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/email/send";
    }
}
