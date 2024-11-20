package com.scm.scm10.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.security.Principal;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
      //  AuthenticationPrincipal principal=(AuthenticationPrincipal) authentication.getPrincipal();
        if(authentication instanceof OAuth2AuthenticationToken){
            //sign in with google
            OAuth2AuthenticationToken authentication1 = (OAuth2AuthenticationToken) authentication;
            var clientId = authentication1.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String username ="";

            if(clientId.equalsIgnoreCase("google")){
                System.out.printf("getting email from google client");
                username=oauth2User.getAttribute("email").toString();
            } else if (clientId.equalsIgnoreCase("github")) {
                System.out.printf("getting email from github client");
                username=oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email").toString() : oauth2User.getAttribute("login").toString()+"@gmail.com";
            }
            return username;
            //sign in with github

            //sign in with linkedin
        }else{
            System.out.printf("getting email from self client");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerified(String emailToken){
        String link = "http://localhost:8080/auth/verify-email?token="+emailToken;
        return link;
    }
}
