package com.cameron.cop3060.repository;

import com.cameron.cop3060.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByIsSubscribedTrue();
    
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.hobbies WHERE u.id = :id")
    Optional<User> findByIdWithHobbies(Long id);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.favoriteMovies WHERE u.id = :id")
    Optional<User> findByIdWithMovies(Long id);
}
