package com.scm.scm20.services;

import com.scm.scm20.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
   // User updateUser(User user)
    Optional< User> getUserById(String id);
    Optional <User >updateUser(User user);
    void deleteUser(String id);
    boolean userExists(String id);
    boolean isUserExistByEmail(String email);
    User getUserByEmail(String email);
    List<User> getAllUsers();
}
