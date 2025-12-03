package com.cameron.cop3060.repository;

import com.cameron.cop3060.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByEmail(String email);
    
    List<Contact> findAllByOrderByCreatedAtDesc();
}
