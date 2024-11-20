package com.scm.scm10.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {


    //dashboard
    @RequestMapping("/dashboard")
    public String dashboard(){
        return "user/dashboard";
    }

    //user profile
    @RequestMapping("/profile")
    public String profile(Model model, Authentication authentication){
        return "user/profile";
    }
    //add contact
    //view contact
    //user edit
    //user delete
    //user search

}
