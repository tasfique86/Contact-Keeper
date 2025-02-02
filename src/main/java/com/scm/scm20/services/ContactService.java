package com.scm.scm20.services;

import com.scm.scm20.entities.Contact;

import java.util.List;

public interface ContactService {
    public Contact saveContact(Contact contact);
    public Contact updateContact(Contact contact);
    List<Contact> getAllContacts();
    Contact getContactById(String id);
    void deleteContact(String id);
    List<Contact> searchContact(String name,String email,String phoneNumber);
    List<Contact> getByUserId(String userId);
}
