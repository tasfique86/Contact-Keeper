package com.scm.scm20.controller;


import com.scm.scm20.forms.EmailForm;
import com.scm.scm20.forms.EmailFormForMultiple;
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

import java.util.Arrays;
import java.util.regex.Pattern;


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








//------------mail for Multi-Person

    @RequestMapping("/send_multiple")
    public String emailForMultiPerson( Model model){

        EmailFormForMultiple emailForm = new EmailFormForMultiple();
     //   String[] em={"camouflagetr1217@gmail.com", "mehrazzhossin@gmail.com,nahidhossainmd99@gmail.com"};
    //    emailForm.setTo(em);
        model.addAttribute("emailForm", emailForm);
        return "user/message_for_multiple";
    }



    @RequestMapping(value = "/send_multiple", method = RequestMethod.POST)
    public String emailForMultiPerson(@Valid
                                      @ModelAttribute EmailFormForMultiple emailForm,
                                      BindingResult bindingResult,
                                      Authentication authentication,
                                      HttpSession httpSession) {



        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("message", Message.builder()
                    .content("Please enter a valid email address")
                    .type(MessageType.red)
                    .build());
            return "user/message";
        }

        // Convert comma-separated emails to array
        String[] emailList = emailForm.getTo()[0].split("\\s*,\\s*"); // Trim spaces around commas

        // Validate each email manually
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        for (String email : emailList) {
            if (!emailPattern.matcher(email).matches()) {
                httpSession.setAttribute("message", Message.builder()
                        .content("Invalid email: " + email)
                        .type(MessageType.red)
                        .build());
                return "user/message";
            }
        }

        logger.info("Emails: " + Arrays.toString(emailList));
        logger.info("Subject: " + emailForm.getSubject());
        logger.info("Body: " + emailForm.getBody());

        // Get logged-in user's email
        String username = Helper.getEmailOfLoggedInUser(authentication);
        String subject = "Sent by: " + username + " || " + emailForm.getSubject();

        // Send email
        emailService.sendEmail(emailList, subject, emailForm.getBody());

        httpSession.setAttribute("message", Message.builder()
                .content("Emails sent successfully!")
                .type(MessageType.green)
                .build());

        return "redirect:/user/email/send_multiple";
    }




}
