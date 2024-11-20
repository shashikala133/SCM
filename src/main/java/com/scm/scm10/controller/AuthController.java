package com.scm.scm10.controller;

import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.Message;
import com.scm.scm10.helpers.MessageType;
import com.scm.scm10.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    // verify email

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(
            @RequestParam("token") String token, HttpSession session) {

        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {

            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", Message.builder()
                        .type(MessageType.green)
                        .content("You email is verified. Now you can login  ")
                        .build());
                return "success_page";
            }

            session.setAttribute("message", Message.builder()
                    .type(MessageType.red)
                    .content("Email not verified ! Token is not associated with user .")
                    .build());
            return "error_page";

        }

        session.setAttribute("message", Message.builder()
                .type(MessageType.red)
                .content("Email not verified ! Token is not associated with user .")
                .build());

        return "error_page";
    }

}