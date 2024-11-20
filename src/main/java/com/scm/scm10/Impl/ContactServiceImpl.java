package com.scm.scm10.Impl;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.ResourceNotFoundException;
import com.scm.scm10.repo.ContactRepo;
import com.scm.scm10.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact contact1 = contactRepo.findById(contact.getId()).orElseThrow(() -> new ResourceNotFoundException("contact not found"));
        contact1.setName(contact.getName());
        contact1.setEmail(contact.getEmail());
        contact1.setPhoneNumber(contact.getPhoneNumber());
        contact1.setAddress(contact.getAddress());
        contact1.setDescription(contact.getDescription());
        contact1.setPicture(contact.getPicture());
        contact1.setFavourite(contact.getFavourite());
        contact1.setWebsiteLink(contact.getWebsiteLink());
        contact1.setLinkedInLink(contact.getLinkedInLink());
        contact1.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());


        return  contactRepo.save(contact1);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact not found with given id: "+id));
    }

    @Override
    public void delete(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id: " + id));
        contactRepo.delete(contact);
    }

    @Override
    public Page<Contact> searchByName(String name, int size, int page, String sortBy, String order, User user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndEmailContaining(user,name,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int size, int page, String sortBy, String order, User user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contact> searchByPhone(String phone, int size, int page, String sortBy, String order, User user) {
        Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phone,pageable);
    }


    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction) {
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page,size,sort);
        return contactRepo.findByUser(user,pageable);
    }
}
