package com.scm.scm20.controller;


import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.helper.AppConstents;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.Message;
import com.scm.scm20.helper.MessageType;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.ImageService;
import com.scm.scm20.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


    Logger logger = LoggerFactory.getLogger(ContactController.class);



//    get method : (call "/user/contacts/add" this url for view the add_contact.html page )------------------------------
    @RequestMapping("/add")
    public String addContact(Model model) {

        ContactForm contactForm = new ContactForm();
//        contactForm.setName("Tasfique");
//        contactForm.setFavorite(true);

        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";

    }
    //  this  post method call from add_contact.html ( in form section) code : (call "/user/contacts/add" this url for to save form data )------------------------------
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm , BindingResult bindingResult,
                              Authentication authentication, HttpSession httpSession) {


        // validation check for input field
        if(bindingResult.hasErrors()) {
            httpSession.setAttribute("message", Message.builder()
                    .content("Please fill all the required fields")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);

        //------------------upload image

        String ImagePublicId= UUID.randomUUID().toString();
        String fileURL=imageService.uploadImage(contactForm.getProfilePicture(),ImagePublicId );



        Contact contact = new Contact();


        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setAddress(contactForm.getAddress());
        contact.setEmail(contactForm.getEmail());
        contact.setDescription(contactForm.getAbout());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setUser(user);
        contact.setLinkdInLink(contactForm.getLinkedInLink());
        contact.setFacebookLink(contactForm.getFacebookLink());

        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(ImagePublicId);


       // logger.info("Profile Picture: "+fileURL);

        contactService.saveContact(contact);


       // System.out.println(contactForm);

        httpSession.setAttribute("message", Message.builder()
                        .content("Your contact has been saved successfully")
                        .type(MessageType.green)
                        .build());
        return"redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContact(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = AppConstents.PAGE_SIZE+"") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {
       String userName= Helper.getEmailOfLoggedInUser(authentication); //--------here userName is email----------
       User user=userService.getUserByEmail(userName);
       Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstents.PAGE_SIZE);
        return "user/view_contacts";
    }

}
