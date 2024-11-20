package com.scm.scm10.controller;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;
import com.scm.scm10.forms.ContactForm;
import com.scm.scm10.forms.ContactSearchForm;
import com.scm.scm10.helpers.AppConstants;
import com.scm.scm10.helpers.Helper;
import com.scm.scm10.helpers.Message;
import com.scm.scm10.helpers.MessageType;
import com.scm.scm10.service.ContactService;
import com.scm.scm10.service.ImageService;
import com.scm.scm10.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;
    @Autowired
    ImageService imageService;

    //add contact page
    @RequestMapping("/add")
    public String addContactView(Model model){
        ContactForm contactForm=new ContactForm();
//        contactForm.setName("shashikala");
//        contactForm.setFavourite(false);
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result, Authentication authentication, HttpSession httpSession){

        //validate
        if(result.hasErrors()){
            httpSession.setAttribute("message", Message.builder()
                            .content("please correct the following errors")
                            .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);



        User userByEmail = userService.getUserByEmail(username);

        log.info("file information: {}",contactForm.getContactImage().getOriginalFilename());
        //image processing

        //process the form data
        Contact contact=new Contact();
        contact.setName(contactForm.getName());
        contact.setFavourite(contactForm.isFavourite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(userByEmail);
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedinLink());

        if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String fileName = UUID.randomUUID().toString();
            //process the contact picture
            String fileUrl=imageService.uploadImage(contactForm.getContactImage(),fileName);
            contact.setPicture(fileUrl);
            contact.setCloudinaryImagePublicId(fileName);
        }

        contactService.save(contact);
        System.out.println(contactForm);

        //set the picture url

        //set message to be displayed on the view
        httpSession.setAttribute("message",Message.builder()
                .content("contact saved successfully")
                .type(MessageType.green)
                .build());


       return "redirect:/user/contacts/add";
    }

    //view contact
    @RequestMapping()
    public String viewContact(@RequestParam(value = "page",defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                              @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                              @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,Authentication authentication){
        String emailOfLoggedInUser = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(emailOfLoggedInUser);
        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm",new ContactSearchForm());
        return "user/contacts";
    }

    @GetMapping("/search")
    public String searchHandler(Model model,
                                @ModelAttribute ContactSearchForm contactSearchForm,
//            @RequestParam("field") String field,
//            @RequestParam("keyword") String keyword,
                                @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
                                @RequestParam(value = "page",defaultValue = "0") int page,
                                @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
                                @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                Authentication authentication
    ){
        log.info("field  {} keyword {}", contactSearchForm.getField(),contactSearchForm.getKeyword());
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
        Page<Contact> pageContact=null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContact=contactService.searchByName(contactSearchForm.getKeyword(),size,page,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact=contactService.searchByEmail(contactSearchForm.getKeyword(),size,page,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact=contactService.searchByPhone(contactSearchForm.getKeyword(),size,page,sortBy,direction,user);
        }
        log.info("Page Contact {}",pageContact);
        model.addAttribute("pageContact",pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm",contactSearchForm);
        return "user/search";
    }
  //delete
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId,HttpSession session){
        contactService.delete(contactId);
        log.info("contact deleted {}",contactId);
        session.setAttribute("message",
                Message.builder()
                        .content("contact is deleted successfully")
                        .type(MessageType.green)
                        .build());
        return "redirect:/user/contacts";
    }

    //update
    @RequestMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable String contactId, Model model){

        Contact contact = contactService.getById(contactId);
        ContactForm contactForm=new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.getFavourite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        model.addAttribute("contactForm",contactForm);
        model.addAttribute("contactId",contactId);
        return "/user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}",method = RequestMethod.POST)
    public String updateContact( @PathVariable String contactId,@Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model ){
        //update
        if (bindingResult.hasErrors()){
            return "/user/update_contact_view";
        }
        Contact con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourite(contactForm.isFavourite());
        con.setWebsiteLink(contactForm.getWebsiteLink());

        if (contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()){
            String filename = UUID.randomUUID().toString();
            String uploadImage = imageService.uploadImage(contactForm.getContactImage(), filename);
            con.setCloudinaryImagePublicId(filename);
         //   con.setPicture(contactForm.getPicture());
            con.setPicture(uploadImage);
            contactForm.setPicture(uploadImage);
        }else {
            log.info("project is empty");
        }

        Contact update = contactService.update(con);
        model.addAttribute("message",
                Message.builder()
                        .content("contact updated")
                        .type(MessageType.green)
                        .build());
        return "redirect:/user/contacts/view/"+contactId;
    }
}
