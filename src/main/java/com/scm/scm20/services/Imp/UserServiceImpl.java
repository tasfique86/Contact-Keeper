package com.scm.scm20.services.Imp;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.AppConstents;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repositories.UserRepositories;
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

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return userRepositories.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepositories.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {

       User updateUser= userRepositories.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));

       updateUser.setName(user.getName());
       updateUser.setEmail(user.getEmail());
       updateUser.setPassword(user.getPassword());
       updateUser.setPhoneNumber(user.getPhoneNumber());
       updateUser.setAbout(user.getAbout());
       updateUser.setProfilePic(user.getProfilePic());
       updateUser.setEnabled(user.isEnabled());
       updateUser.setEmailVerified(user.isEmailVerified());
       updateUser.setPhoneNumberVerified(user.isPhoneNumberVerified());
       updateUser.setProviders(user.getProviders());
       updateUser.setProviderUserId(user.getProviderUserId());

        User save= userRepositories.save(updateUser);

        return Optional.ofNullable(save);


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
