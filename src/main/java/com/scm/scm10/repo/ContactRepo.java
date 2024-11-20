package com.scm.scm10.repo;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String> {
    Page<Contact> findByUser(User user, Pageable pageable);
    @Query("SELECT c FROM Contact c where c.user.id=:userId")
    List<Contact> findByUserId(@Param("userId") String id);

    Page<Contact> findByUserAndNameContaining(User user,String name,Pageable pageable);
    Page<Contact> findByUserAndEmailContaining(User user,String email, Pageable pageable);
    Page<Contact> findByUserAndPhoneNumberContaining(User user,String phoneNumber,Pageable pageable);


}
