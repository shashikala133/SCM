package com.scm.scm10.controller;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId){
      return contactService.getById(contactId);
    }
}
