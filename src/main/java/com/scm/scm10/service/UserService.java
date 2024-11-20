package com.scm.scm10.service;

import java.util.List;
import java.util.Optional;

import com.scm.scm10.entities.User;

public interface UserService {
   User saveUser(User user);
   Optional<User> getUserById(String id);
   Optional<User> updateUser(User user);
   void deleteUser(String id);
   boolean isUserExist(String id);
   boolean isUserExistByEmail(String emailId);
   List<User> getAllUsers();
   User getUserByEmail(String email);
}
