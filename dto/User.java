package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User Entity - Replaces temporary storage with persistent table
 * 
 * RELATIONSHIPS:
 * - ONE-TO-MANY: User -> Hobbies (One user has many hobbies)
 * - ONE-TO-MANY: User -> Contacts (One user has many contact submissions)
 * - MANY-TO-MANY: User <-> Movies (Users can favorite many movies)
 * 
 * @author Cameron Brown
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true),
    @Index(name = "idx_user_name", columnList = "last_name, first_name")
})
public class User {

    // ==========================================
    // PRIMARY KEY
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // VALIDATED FIELDS
    // ==========================================
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "First name can only contain letters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Last name can only contain letters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed = false;

    @Size(max = 50, message = "Browser name cannot exceed 50 characters")
    @Column(length = 50)
    private String browser;

    @Column(name = "likes_site")
    private Boolean likesSite;

    // ==========================================
    // TIMESTAMP FIELDS
    // ==========================================
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==========================================
    // ONE-TO-MANY: User -> Hobbies
    // A user can have multiple hobbies
    // ==========================================
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("user")
    private List<Hobby> hobbies = new ArrayList<>();

    // ==========================================
    // ONE-TO-MANY: User -> Contacts
    // A user can submit multiple contact forms
    // ==========================================
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("user")
    private List<Contact> contacts = new ArrayList<>();

    // ==========================================
    // MANY-TO-MANY: User <-> Movies
    // Users can favorite many movies, movies can be favorited by many users
    // ==========================================
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_favorite_movies",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    @JsonIgnoreProperties("favoritedByUsers")
    private Set<Movie> favoriteMovies = new HashSet<>();

    // ==========================================
    // LIFECYCLE CALLBACKS
    // ==========================================
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    public User() {}

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // ==========================================
    // RELATIONSHIP HELPER METHODS
    // ==========================================
    
    // Hobby helpers (One-to-Many)
    public void addHobby(Hobby hobby) {
        hobbies.add(hobby);
        hobby.setUser(this);
    }

    public void removeHobby(Hobby hobby) {
        hobbies.remove(hobby);
        hobby.setUser(null);
    }

    // Contact helpers (One-to-Many)
    public void addContact(Contact contact) {
        contacts.add(contact);
        contact.setUser(this);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
        contact.setUser(null);
    }

    // Movie helpers (Many-to-Many)
    public void addFavoriteMovie(Movie movie) {
        this.favoriteMovies.add(movie);
        movie.getFavoritedByUsers().add(this);
    }

    public void removeFavoriteMovie(Movie movie) {
        this.favoriteMovies.remove(movie);
        movie.getFavoritedByUsers().remove(this);
    }

    // Utility method
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================
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

    public List<Hobby> getHobbies() { return hobbies; }
    public void setHobbies(List<Hobby> hobbies) { this.hobbies = hobbies; }

    public List<Contact> getContacts() { return contacts; }
    public void setContacts(List<Contact> contacts) { this.contacts = contacts; }

    public Set<Movie> getFavoriteMovies() { return favoriteMovies; }
    public void setFavoriteMovies(Set<Movie> favoriteMovies) { this.favoriteMovies = favoriteMovies; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + getFullName() + "', email='" + email + "'}";
    }
}
