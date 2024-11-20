package com.scm.scm10.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.scm.scm10.helpers.AppConstants;
import com.scm.scm10.helpers.Helper;
import com.scm.scm10.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.ResourceNotFoundException;
import com.scm.scm10.repo.UserRepository;
import com.scm.scm10.service.UserService;

@Service
public class UserServiceImpl implements UserService{
 // cntl+. --> add unimplemented methods

 @Autowired
 UserRepository userRepository;

 @Autowired
 private EmailService emailService;

 private Logger logger=LoggerFactory.getLogger(this.getClass());
@Autowired
 PasswordEncoder passwordEncoder;
    @Override
    public User saveUser(User user) {
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of(AppConstants.APP_ROLE));

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User save = userRepository.save(user);
        String emailLink = Helper.getLinkForEmailVerified(emailToken);
        emailService.sendEmail(save.getEmail(), "Verify account : smart contact manager",emailLink);
        return save;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user1 = userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        user1.setName(user.getName());
        user1.setAbout(user.getAbout());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setProfilePic(user.getProfilePic());
        user1.setEmailVerified(user.getEmailVerified());
        user1.setEnabled(user.isEnabled());
        user1.setPhoneVerified(user.getPhoneVerified());
        user1.setProviders(user.getProviders());
        user1.setProviderUserId(user.getProviderUserId());
        User savedUser = userRepository.save(user1);
        return Optional.ofNullable(savedUser);
    }

    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public boolean isUserExist(String id) {
       User user = userRepository.findById(id).orElse(null);
        return user!=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String emailId) {
        User user = userRepository.findByEmail(emailId).orElse(null);
        return user!=null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


}
