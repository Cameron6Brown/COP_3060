package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Genre is required")
    @Size(max = 50)
    @Column(nullable = false)
    private String genre;

    @NotNull(message = "Year is required")
    @Min(value = 1888, message = "Year must be 1888 or later")
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    @Column(name = "year_released", nullable = false)
    private Integer yearReleased;

    @Size(max = 1000)
    private String description;

    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // MANY-TO-MANY: Movie <-> Users
    @ManyToMany(mappedBy = "favoriteMovies")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public Movie() {}

    public Movie(String title, String genre, Integer yearReleased) {
        this.title = title;
        this.genre = genre;
        this.yearReleased = yearReleased;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getYearReleased() { return yearReleased; }
    public void setYearReleased(Integer yearReleased) { this.yearReleased = yearReleased; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public Set<User> getUsers() { return users; }
    public void setUsers(Set<User> users) { this.users = users; }
}
