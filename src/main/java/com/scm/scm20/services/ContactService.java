package com.scm.scm20.services;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import org.springframework.data.domain.Page;


import java.util.List;

public interface ContactService {
    public Contact saveContact(Contact contact);
    public Contact updateContact(Contact contact);
    List<Contact> getAllContacts();
    Contact getContactById(String id);
    void deleteContact(String id);
    List<Contact> searchContact(String name,String email,String phoneNumber);
    List<Contact> getByUserId(String userId);
   Page<Contact> getByUser(User user, int page, int size,String sortBy,String direction);
}
