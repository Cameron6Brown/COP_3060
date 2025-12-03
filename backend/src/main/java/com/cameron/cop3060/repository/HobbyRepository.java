package com.cameron.cop3060.repository;

import com.cameron.cop3060.entity.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HobbyRepository extends JpaRepository<Hobby, Long> {

    List<Hobby> findByUserId(Long userId);
    
    List<Hobby> findByCategory(Hobby.Category category);
    
    List<Hobby> findByNameContainingIgnoreCase(String name);
}
