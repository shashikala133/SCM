package com.scm.scm10.service;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ContactService {
    Contact save(Contact contact);
    Contact update(Contact contact);
    List<Contact> getAll();
    Contact getById(String id);
    void delete(String id);
    Page<Contact> searchByName(String name, int size, int page,String sortBy,String order, User user);
    Page<Contact> searchByEmail(String email,int size, int page,String sortBy,String order, User user);
    Page<Contact> searchByPhone(String phone,int size, int page,String sortBy,String order, User user);
    List<Contact> getByUserId(String userId);
    Page<Contact> getByUser(User user, int page, int size, String sortBy, String order);
}
