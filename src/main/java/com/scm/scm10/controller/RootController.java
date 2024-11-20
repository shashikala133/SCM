package com.scm.scm10.controller;

import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.Helper;
import com.scm.scm10.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class RootController {

    @Autowired
    UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if(authentication==null){
            return;
        }
        System.out.println("Adding logged in user to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        log.info("user logged in : " + username);
        User userByEmail = userService.getUserByEmail(username);
        System.out.println(userByEmail);
        System.out.printf(userByEmail.getEmail());
        System.out.printf(userByEmail.getName());
        model.addAttribute("loggedInUser", userByEmail);


    }
}
