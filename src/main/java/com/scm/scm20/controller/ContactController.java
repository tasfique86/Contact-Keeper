package com.scm.scm20.controller;


import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
//    get method : (call "/user/contacts/add" this url for view the add_contact.html page )------------------------------
    @RequestMapping("/add")
    public String addContact(Model model) {

        ContactForm contactForm = new ContactForm();
        contactForm.setName("Tasfique");
        contactForm.setFavorite(true);

        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";

    }
    //  this  post method call from add_contact.html ( in form section) code : (call "/user/contacts/add" this url for to save form data )------------------------------
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@ModelAttribute ContactForm contactForm , Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(username);


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

        contactService.saveContact(contact);
        System.out.println(contactForm);
        return"redirect:/user/contacts/add";
    }

}
