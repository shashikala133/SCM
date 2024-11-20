package com.scm.scm10.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.scm10.entities.User;

public interface UserRepository extends JpaRepository<User,String>{
    Optional<User> findByEmail(String emailId);

    Optional<User> findByEmailAndPassword(String email, String password);

    Optional<User> findByEmailToken(String token);
}
