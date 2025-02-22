package com.scm.scm20.services.Imp;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstents;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repositories.UserRepositories;
import com.scm.scm20.services.EmailService;
import com.scm.scm20.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepositories userRepositories;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public User saveUser(User user) {

        //user id: have to generate
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        //password encoder
        //set user password after encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user.setPassword(userId);

        //set user role
        user.setRoleList(List.of(AppConstents.ROLE_USER));


        String emailToken= UUID.randomUUID().toString();
        String emailLink= Helper.getLinkForEmailVerification(emailToken);

        user.setEmailToken(emailToken);

        User saveUser= userRepositories.save(user);

        emailService.sendEmail(saveUser.getEmail(),"Contact Keeper account Verification link",emailLink);

        return saveUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepositories.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

        logger.info("Updating userId: " + user.getUserId());
        logger.info("Upadating phone : " +user.getPhoneNumber());
        logger.info("Updating birthday : " +user.getBirthday());

       User updateUser= userRepositories.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));



       updateUser.setName(user.getName());

        logger.info("old userName: " + updateUser.getName());
        logger.info("old userEmail: " + updateUser.getEmail());
        logger.info("old userPhoneNumber: " + updateUser.getPhoneNumber());
        logger.info("old userBirthday: " + updateUser.getBirthday());

       updateUser.setUserId(user.getUserId());

       updateUser.setEmail(user.getEmail());


       updateUser.setPhoneNumber(user.getPhoneNumber());
       updateUser.setBirthday(user.getBirthday());
       updateUser.setAddress(user.getAddress());
       updateUser.setAbout(user.getAbout());

       updateUser.setProfilePic(user.getProfilePic());

       updateUser.setEnabled(user.isEnabled());

       updateUser.setEmailVerified(user.isEmailVerified());
       updateUser.setPhoneNumberVerified(user.isPhoneNumberVerified());



       updateUser.setProviders(user.getProviders());
       updateUser.setProviderUserId(user.getProviderUserId());

        updateUser.setPassword(user.getPassword());



        return Optional.of(userRepositories.save(updateUser));


    }

    @Override
    public void deleteUser(String id) {
        User user= userRepositories.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepositories.delete(user);
    }

    @Override
    public boolean userExists(String id) {
        User user2 = userRepositories.findById(id).orElse(null);
        return user2 != null;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user=userRepositories.findByEmail(email).orElse(null);
        return user!=null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepositories.findByEmail(email).orElse(null);
    }
}
