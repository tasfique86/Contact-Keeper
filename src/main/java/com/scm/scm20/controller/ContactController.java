package com.scm.scm20.controller;


import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.ContactSearchForm;
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
import org.springframework.web.bind.annotation.*;

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
    public String addContact(Model model)
    {

        ContactForm contactForm = new ContactForm();
//        contactForm.setName("Tasfique");
//        contactForm.setEmail("Tasfique@");
//        contactForm.setFavorite(true);

        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";

    }


    //  this  post method call from add_contact.html ( in form section) code : (call "/user/contacts/add" this url for to save form data )------------------------------
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm , BindingResult bindingResult,
                              Authentication authentication, HttpSession httpSession)
    {

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


        //------------------upload image
        if(contactForm.getProfilePicture()==null || !contactForm.getProfilePicture().isEmpty()) {
            String ImagePublicId= UUID.randomUUID().toString();
            String fileURL=imageService.uploadImage(contactForm.getProfilePicture(),ImagePublicId );

            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(ImagePublicId);
        }



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
            Authentication authentication)
    {
       String userName= Helper.getEmailOfLoggedInUser(authentication); //--------here userName is email----------
       User user=userService.getUserByEmail(userName);
       Page<Contact> contactPage = contactService.getByUser(user,page,size,sortBy,direction);

        model.addAttribute("contactPage", contactPage);
        model.addAttribute("pageSize", AppConstents.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/view_contacts";
    }


    @RequestMapping("/search")
    public String searchContact(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = AppConstents.PAGE_SIZE+"") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model,
            Authentication authentication
            )
    {

        var field = contactSearchForm.getField();
        var value = contactSearchForm.getKeyword();


        logger.info("Field {} keyword {}", field , value);

        User currrentUser=userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> contactPage=null;
        if(field.equalsIgnoreCase("name")) {

            contactPage=contactService.searchByName(value,page,size,sortBy,direction,currrentUser);

        }
        else if(field.equalsIgnoreCase("email")) {
            contactPage=contactService.searchByEmail(value,page,size,sortBy,direction,currrentUser);
        }
        else if(field.equalsIgnoreCase("phoneNumber")) {
            contactPage=contactService.searchByPhoneNumber(value,page,size,sortBy,direction,currrentUser);
        }


        logger.info("ContactPage  {}", contactPage);

        model.addAttribute("contactPage", contactPage);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageSize", AppConstents.PAGE_SIZE);
        return "user/search";
    }

    //delete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") String contactId ,
                                HttpSession httpSession)
    {
        logger.info("Contact {} deleted", contactId);
        contactService.deleteContact(contactId);

        httpSession.setAttribute("message",
                Message.builder()
                        .content("Contact has been deleted successfully")
                        .type(MessageType.green)
                        .build()
        );
        return "redirect:/user/contacts";
    }

//    update contact
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model){

        var contact=contactService.getContactById(contactId);

        ContactForm contactForm=new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setAddress(contact.getAddress());
        contactForm.setEmail(contact.getEmail());
        contactForm.setAbout(contact.getDescription());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setLinkedInLink(contact.getLinkdInLink());
        contactForm.setFacebookLink(contact.getFacebookLink());
        contactForm.setPictureUrl(contact.getPicture());


        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

//update contact submit response
    @RequestMapping(value ="/update/{contactId}",method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
                                @Valid
                                @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                HttpSession httpSession,
                                Model model){

        if(bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

       Contact newContact = contactService.getContactById(contactId);

       newContact.setId(contactId);

       newContact.setName(contactForm.getName());
       newContact.setFavorite(contactForm.isFavorite());
       newContact.setAddress(contactForm.getAddress());
       newContact.setEmail(contactForm.getEmail());
       newContact.setFacebookLink(contactForm.getFacebookLink());
       newContact.setLinkdInLink(contactForm.getLinkedInLink());
       newContact.setPhoneNumber(contactForm.getPhoneNumber());
       newContact.setDescription(contactForm.getAbout());




       //newContact.setCloudinaryImagePublicId(contactForm.getPictureUrl());

//        image proccess
        if(contactForm.getProfilePicture() !=null && !contactForm.getProfilePicture().isEmpty() ){

            String fileName=UUID.randomUUID().toString();
            String imageURL= imageService.uploadImage(contactForm.getProfilePicture(),fileName);

            newContact.setCloudinaryImagePublicId(fileName);
            newContact.setPicture(imageURL);
            contactForm.setPictureUrl(imageURL);
        }

        var updateContact= contactService.updateContact(newContact);
        logger.info("Contact {} updated", updateContact);
        httpSession.setAttribute("message", Message.builder()
                .content("Contact has been updated successfully")
                .type(MessageType.green)
                .build());
        return "redirect:/user/contacts/view/" + contactId;
    }


}
