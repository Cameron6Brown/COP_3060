package com.cameron.cop3060.service;

import com.cameron.cop3060.entity.Hobby;
import com.cameron.cop3060.entity.Movie;
import com.cameron.cop3060.entity.User;
import com.cameron.cop3060.exception.ResourceNotFoundException;
import com.cameron.cop3060.repository.HobbyRepository;
import com.cameron.cop3060.repository.MovieRepository;
import com.cameron.cop3060.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HobbyRepository hobbyRepository;

    // CRUD Operations
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setIsSubscribed(userDetails.getIsSubscribed());
        user.setBrowser(userDetails.getBrowser());
        user.setLikesSite(userDetails.getLikesSite());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // Relationship Operations
    public User addHobbyToUser(Long userId, Hobby hobby) {
        User user = getUserById(userId);
        user.addHobby(hobby);
        return userRepository.save(user);
    }

    public User addFavoriteMovie(Long userId, Long movieId) {
        User user = userRepository.findByIdWithMovies(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        user.addFavoriteMovie(movie);
        return userRepository.save(user);
    }

    public User removeFavoriteMovie(Long userId, Long movieId) {
        User user = userRepository.findByIdWithMovies(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId)
            .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        user.removeFavoriteMovie(movie);
        return userRepository.save(user);
    }
}
