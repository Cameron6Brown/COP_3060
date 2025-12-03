package com.cameron.cop3060.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed = false;

    private String browser;

    @Column(name = "likes_site")
    private Boolean likesSite;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ONE-TO-MANY: User -> Hobbies
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Hobby> hobbies = new HashSet<>();

    // MANY-TO-MANY: User <-> Movies
    @ManyToMany
    @JoinTable(
        name = "user_favorite_movies",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    private Set<Movie> favoriteMovies = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Helper methods
    public void addHobby(Hobby hobby) {
        hobbies.add(hobby);
        hobby.setUser(this);
    }

    public void removeHobby(Hobby hobby) {
        hobbies.remove(hobby);
        hobby.setUser(null);
    }

    public void addFavoriteMovie(Movie movie) {
        favoriteMovies.add(movie);
        movie.getUsers().add(this);
    }

    public void removeFavoriteMovie(Movie movie) {
        favoriteMovies.remove(movie);
        movie.getUsers().remove(this);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getIsSubscribed() { return isSubscribed; }
    public void setIsSubscribed(Boolean isSubscribed) { this.isSubscribed = isSubscribed; }

    public String getBrowser() { return browser; }
    public void setBrowser(String browser) { this.browser = browser; }

    public Boolean getLikesSite() { return likesSite; }
    public void setLikesSite(Boolean likesSite) { this.likesSite = likesSite; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Set<Hobby> getHobbies() { return hobbies; }
    public void setHobbies(Set<Hobby> hobbies) { this.hobbies = hobbies; }

    public Set<Movie> getFavoriteMovies() { return favoriteMovies; }
    public void setFavoriteMovies(Set<Movie> favoriteMovies) { this.favoriteMovies = favoriteMovies; }
}
