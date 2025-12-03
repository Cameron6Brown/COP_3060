package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Movie Entity - Replaces temporary storage with persistent table
 * 
 * RELATIONSHIPS:
 * - MANY-TO-MANY: Movie <-> Users (Movies can be favorited by many users)
 * - MANY-TO-MANY: Movie <-> Genres (Movies can have multiple genres)
 * 
 * @author Cameron Brown
 */
@Entity
@Table(name = "movies", indexes = {
    @Index(name = "idx_movie_title", columnList = "title"),
    @Index(name = "idx_movie_year", columnList = "year_released"),
    @Index(name = "idx_movie_rating", columnList = "rating")
})
public class Movie {

    // ==========================================
    // PRIMARY KEY
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // VALIDATED FIELDS
    // ==========================================
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "Genre is required")
    @Size(max = 100, message = "Genre cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String genre;

    @NotNull(message = "Year released is required")
    @Min(value = 1888, message = "Year must be 1888 or later")
    @Max(value = 2100, message = "Year must be 2100 or earlier")
    @Column(name = "year_released", nullable = false)
    private Integer yearReleased;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Column(length = 2000)
    private String description;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10.0")
    @Column(precision = 3, scale = 1)
    private Double rating;

    @Size(max = 100, message = "Director name cannot exceed 100 characters")
    @Column(length = 100)
    private String director;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1000, message = "Duration cannot exceed 1000 minutes")
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    @Column(name = "image_url", length = 500)
    private String imageUrl;

    // ==========================================
    // TIMESTAMP FIELDS
    // ==========================================
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==========================================
    // MANY-TO-MANY: Movie <-> Users
    // ==========================================
    @ManyToMany(mappedBy = "favoriteMovies", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("favoriteMovies")
    private Set<User> favoritedByUsers = new HashSet<>();

    // ==========================================
    // MANY-TO-MANY: Movie <-> Genres
    // ==========================================
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @JsonIgnoreProperties("movies")
    private Set<Genre> genres = new HashSet<>();

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
    public Movie() {}

    public Movie(String title, String genre, Integer yearReleased) {
        this.title = title;
        this.genre = genre;
        this.yearReleased = yearReleased;
    }

    public Movie(String title, String genre, Integer yearReleased, String description, Double rating) {
        this.title = title;
        this.genre = genre;
        this.yearReleased = yearReleased;
        this.description = description;
        this.rating = rating;
    }

    // ==========================================
    // RELATIONSHIP HELPER METHODS
    // ==========================================
    public void addGenre(Genre genreEntity) {
        this.genres.add(genreEntity);
        genreEntity.getMovies().add(this);
    }

    public void removeGenre(Genre genreEntity) {
        this.genres.remove(genreEntity);
        genreEntity.getMovies().remove(this);
    }

    public int getFavoritesCount() {
        return favoritedByUsers.size();
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================
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

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Set<User> getFavoritedByUsers() { return favoritedByUsers; }
    public void setFavoritedByUsers(Set<User> favoritedByUsers) { this.favoritedByUsers = favoritedByUsers; }

    public Set<Genre> getGenres() { return genres; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }

    @Override
    public String toString() {
        return "Movie{id=" + id + ", title='" + title + "', year=" + yearReleased + "}";
    }
}
