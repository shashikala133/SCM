package com.scm.scm10.controller;

import com.scm.scm10.helpers.Message;
import com.scm.scm10.helpers.MessageType;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm10.entities.User;
import com.scm.scm10.forms.UserForm;
import com.scm.scm10.service.UserService;

@Controller
public class PageController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("youtubeChannel", "Learn Code With Durgesh");
        model.addAttribute("githubRepo", "https://github.com/learncodewithdurgesh/");
        return "home";
    }

    // about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        System.out.println("login page is loading...");
        return "login";
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        // userForm.setName("Shashi");
        // userForm.setAbout("This is about : Write something about yourself");
     model.addAttribute("userForm", userForm);

        return "register";
    }

    @RequestMapping(value="/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession httpSession){
        System.out.println("processing registration");
        System.out.println(userForm);
        if (bindingResult.hasErrors()){
            return "register";
        }
        //fetch the data
        //validate the data
//        User user = User.builder()
//        .name(userForm.getName())
//        .email(userForm.getEmail())
//        .about(userForm.getAbout())
//        .password(userForm.getPassword())
//        .phoneNumber(userForm.getPhoneNumber())
//        .profilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqf0Wx4wmsKfLYsiLdBx6H4D8bwQBurWhx5g&s")
//        .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqf0Wx4wmsKfLYsiLdBx6H4D8bwQBurWhx5g&s");

        User savedUser = userService.saveUser(user);
        System.out.println(savedUser);
        //save the data
       Message message= Message.builder().content("Registartion successfull").type(MessageType.green).build();
       httpSession.setAttribute("message",message);
        //send message
        //redirect
        return "redirect:/register";
    }
}


