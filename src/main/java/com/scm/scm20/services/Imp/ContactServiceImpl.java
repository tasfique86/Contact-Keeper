package com.scm.scm20.services.Imp;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repositories.ContactRepo;
import com.scm.scm20.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

         var contactOld=   contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

         contactOld.setName(contact.getName());
         contactOld.setEmail(contact.getEmail());
         contactOld.setAddress(contact.getAddress());
         contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());
         contactOld.setPicture(contact.getPicture());
         contactOld.setFacebookLink(contact.getFacebookLink());
         contactOld.setSocialLinks(contact.getSocialLinks());
         contactOld.setDescription(contact.getDescription());
         contactOld.setPhoneNumber(contact.getPhoneNumber());
         contactOld.setFavorite(contact.isFavorite() );

       //  contactOld.setSocialLinks(contact.getSocialLinks());

         return contactRepo.save(contactOld);

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
    public Page<Contact> searchByName(String nameKeyword, int page, int size, String sortBy, String direction,User currentUser) {
        Sort sort = Objects.equals(direction, "desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByNameContainingAndUser(nameKeyword,pageable,currentUser);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int page, int size, String sortBy, String direction,User currentUser) {
        Sort sort = Objects.equals(direction, "desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByEmailContainingAndUser(emailKeyword,pageable,currentUser);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int page, int size, String sortBy, String direction,User currentUser) {
        Sort sort = Objects.equals(direction, "desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByPhoneNumberContainingAndUser(phoneNumberKeyword,pageable,currentUser);
    }





    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size,String sortBy,String direction) {

        Sort sort = Objects.equals(direction, "desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size,sort);
        return contactRepo.findByUser(user,pageable);
    }
}
