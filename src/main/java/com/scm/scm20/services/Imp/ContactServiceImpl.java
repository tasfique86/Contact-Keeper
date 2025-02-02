package com.scm.scm20.services.Imp;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repositories.ContactRepo;
import com.scm.scm20.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;


    @Override
    public Contact saveContact(Contact contact) {
        String contactId= UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
        return null;
    }

    @Override
    public List<Contact> getAllContacts() {
        return List.of();
    }

    @Override
    public Contact getContactById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found, with this id: " + id));
    }

    @Override
    public void deleteContact(String id) {
       var contact = contactRepo.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Contact not found, with this id: " + id));
       contactRepo.delete(contact);

        //we can delete directly this way
        // contactRepo.deleteById(id);
    }

    @Override
    public List<Contact> searchContact(String name, String email, String phoneNumber) {
        return List.of();
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }
}
