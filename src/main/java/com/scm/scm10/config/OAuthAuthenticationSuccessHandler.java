package com.scm.scm10.config;

import com.scm.scm10.entities.Providers;
import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.AppConstants;
import com.scm.scm10.repo.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
     log.info("OAuthAuthenticationSuccessHandler");
   //  response.sendRedirect("/home");
        //save data in database

//        log.info(defaultOAuth2User.getName());
//       defaultOAuth2User.getAttributes().forEach((key,value)->{
//           log.info("{} -> {} ", key,value);
//       });
//       log.info(defaultOAuth2User.getAuthorities().toString());

        //identify the provider
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String authorizedClientRegistrationId = token.getAuthorizedClientRegistrationId();
        log.info(authorizedClientRegistrationId);
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        oAuth2User.getAttributes().forEach((key, value)->{
            log.info("key: "+ key + "value: "+value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoles(List.of(AppConstants.APP_ROLE));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy password");
        //google
        if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
            user.setEmail(oAuth2User.getAttribute("email").toString());
            user.setProfilePic(oAuth2User.getAttribute("picture").toString());
            user.setName(oAuth2User.getAttribute("name").toString());
            user.setProviderUserId(oAuth2User.getName());
            user.setProviders(Providers.GOOGLE);
            user.setAbout("This account is created using google");


        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            //github
            String email = oAuth2User.getAttribute("email")!=null ? oAuth2User.getAttribute("email").toString() : oAuth2User.getAttribute("login").toString()+"@gmail.com";
            String picture = oAuth2User.getAttribute("avatar_url").toString();
            String name = oAuth2User.getAttribute("login").toString();
            String providerUserId = oAuth2User.getName();
            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserId(providerUserId);
            user.setProviders(Providers.GITHUB);
            user.setAbout("This account is created using github");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
           //linkedin


        }else {
           log.info("unknown provider");
        }



        //facebook






        /*
        DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User)authentication.getPrincipal();
        String email = defaultOAuth2User.getAttribute("email").toString();
        String name = defaultOAuth2User.getAttribute("name").toString();
        String picture = defaultOAuth2User.getAttribute("picture").toString();

        //create user and save in database
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setProfilePic(picture);
        user.setPassword("password");
        user.setUserId(UUID.randomUUID().toString());
        user.setProviders(Providers.GOOGLE);
        user.setEnabled(true);
        user.setEmailVerified(true);
        user.setProviderUserId(user.getName());
        user.setRoles(List.of(AppConstants.APP_ROLE));
        user.setAbout("this account is created using google..");
        User user1 = userRepository.findByEmail(email).orElse(null);
        if(user1==null){
            userRepository.save(user);
            log.info("user saved: "+email);
        }

         */

        User user1 = userRepository.findByEmail(user.getEmail()).orElse(null);
        if(user1==null){
            userRepository.save(user);
            log.info("user saved: "+user.getEmail());
        }
        new DefaultRedirectStrategy().sendRedirect(request,response,"/user/profile");
    }
}
