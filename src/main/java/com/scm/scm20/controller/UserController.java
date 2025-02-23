package com.scm.scm20.controller;

import com.scm.scm20.entities.Categories;
import com.scm.scm20.entities.Dashboard;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.UserForm;
import com.scm.scm20.forms.UserFormUpdate;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;




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
    public String userDashboradRequest(Model model, Authentication authentication ){

        String currentUserEmail= Helper.getEmailOfLoggedInUser(authentication);
        logger.info("Load user information from "+currentUserEmail);
        User user=userService.getUserByEmail(currentUserEmail);


        int totalContacts = contactService.getByUserId(user.getUserId()).size();
        int totalFavoriteConatacts = contactService.getByUserIdSearchByFavorite(user.getUserId()).size();

        logger.info("Total contacts: "+totalContacts);
        logger.info("Total favorite contacts: "+totalFavoriteConatacts);

        logger.info(Categories.SIR.toString());

        Dashboard dashboard=new Dashboard();
        dashboard.setTotalContact(totalContacts);
        dashboard.setTotalFavourite(totalFavoriteConatacts);
        model.addAttribute("dashboard",dashboard);

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




    @GetMapping("/profile/update")
    public String detailsUpdateRequest(
            Model model,
            Authentication authentication){
        String username= Helper.getEmailOfLoggedInUser(authentication);


        User user=userService.getUserByEmail(username);

        logger.info("User logged in : {}",username);
        logger.info("User name: "+user.getName());
        logger.info("user phone number: "+user.getPhoneNumber());


        UserFormUpdate userForm=new UserFormUpdate();

        userForm.setUserId(user.getUserId());
        userForm.setEmail(user.getEmail());
        userForm.setName(user.getName());


        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setBirthday(user.getBirthday());
        userForm.setAddress(user.getAddress());
        userForm.setProfilePictureURL(user.getProfilePic());
        userForm.setAbout(user.getAbout());

        userForm.setEnabled(user.isEnabled());
        userForm.setEmailVerified(user.isEmailVerified());
        userForm.setPhoneNumberVerified(user.isPhoneNumberVerified());

        userForm.setProviderUserId(user.getProviderUserId());
        userForm.setProviders(user.getProviders());


        userForm.setPassword(user.getPassword());

      //  logger.info("User  : {}",userForm.getUserId());



        model.addAttribute("userForm",userForm);
        model.addAttribute("hideField ",true);
        return "user/user_profile_update";
    }



    @PostMapping("/profile/update")
    public String updateProfileRequest(@Valid
                                           @ModelAttribute UserFormUpdate userForm,
                                       BindingResult bindingResult,
                                       HttpSession httpSession,
                                       Model model,Authentication authentication){

        if(bindingResult.hasErrors()) {
            logger.info("from update  user password : "+userForm.getPassword()+"  user email"+userForm.getEmail());
            logger.info("user name "+userForm.getName());
            logger.error(bindingResult.getAllErrors().toString());

            httpSession.setAttribute("message", Message.builder()
                    .content("Unsuccessfully !!! Something went wrong !!!")
                    .type(MessageType.red)
                    .build());
            return "user/user_profile_update";
        }



        String userName = Helper.getEmailOfLoggedInUser(authentication);
        User user=userService.getUserByEmail(userName);

        logger.info("User name: "+user.getName());
        logger.info("user phone number: "+user.getPhoneNumber());


        logger.info("User name: "+userForm.getName());
        logger.info("user phone number: "+userForm.getPhoneNumber());

       user.setUserId(userForm.getUserId());

       user.setName(userForm.getName());
       user.setEmail(userForm.getEmail());
       user.setPhoneNumber(userForm.getPhoneNumber());


       user.setBirthday(userForm.getBirthday());
       user.setAddress(userForm.getAddress());
       user.setAbout(userForm.getAbout());

       user.setEnabled(userForm.isEnabled());
       user.setEmailVerified(userForm.isEmailVerified());
       user.setPhoneNumberVerified(userForm.isPhoneNumberVerified());



       user.setProviderUserId(userForm.getProviderUserId());
       user.setProviders(userForm.getProviders());



        user.setPassword(userForm.getPassword());


        //        image proccess
        if(userForm.getProfilePictureFile() !=null && !userForm.getProfilePictureFile().isEmpty() ){

            String fileName= UUID.randomUUID().toString();
            String imageURL= imageService.uploadImage(userForm.getProfilePictureFile(),fileName);

            user.setCloudinaryImagePublicId(fileName);
            user.setProfilePic(imageURL);
            userForm.setProfilePictureURL(imageURL);
        }

        var updateUser = userService.updateUser(user);

        logger.info("Contact {} updated", updateUser);
        httpSession.setAttribute("message", Message.builder()
                .content("User has been updated successfully")
                .type(MessageType.green)
                .build());



        return "redirect:/user/profile/update";
    }





}
