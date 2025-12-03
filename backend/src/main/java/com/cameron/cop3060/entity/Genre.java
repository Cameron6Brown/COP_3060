package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Genre Entity - Represents movie genres
 * 
 * RELATIONSHIPS:
 * - MANY-TO-MANY: Genre <-> Movies (A genre can have many movies, a movie can have many genres)
 * 
 * @author Cameron Brown
 */
@Entity
@Table(name = "genres", indexes = {
    @Index(name = "idx_genre_name", columnList = "name", unique = true),
    @Index(name = "idx_genre_tmdb_id", columnList = "tmdb_id")
})
public class Genre {

    // ==========================================
    // PRIMARY KEY
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // VALIDATED FIELDS
    // ==========================================
    @NotBlank(message = "Genre name is required")
    @Size(min = 2, max = 50, message = "Genre name must be between 2 and 50 characters")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(length = 500)
    private String description;

    // External API ID (from TMDB)
    @Column(name = "tmdb_id", unique = true)
    private Integer tmdbId;

    // Icon or image URL for the genre
    @Size(max = 500, message = "Icon URL cannot exceed 500 characters")
    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    // Color code for UI display
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid color code format")
    @Column(name = "color_code", length = 7)
    private String colorCode;

    // Whether this genre is active/visible
    @Column(name = "is_active")
    private Boolean isActive = true;

    // ==========================================
    // TIMESTAMP FIELDS
    // ==========================================
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==========================================
    // MANY-TO-MANY: Genre <-> Movies
    // A genre can contain many movies
    // ==========================================
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genres")
    private Set<Movie> movies = new HashSet<>();

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
    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Genre(String name, Integer tmdbId) {
        this.name = name;
        this.tmdbId = tmdbId;
    }

    public Genre(String name, String description, Integer tmdbId) {
        this.name = name;
        this.description = description;
        this.tmdbId = tmdbId;
    }

    // ==========================================
    // RELATIONSHIP HELPER METHODS
    // ==========================================
    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getGenres().add(this);
    }

    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getGenres().remove(this);
    }

    public int getMovieCount() {
        return movies.size();
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getTmdbId() { return tmdbId; }
    public void setTmdbId(Integer tmdbId) { this.tmdbId = tmdbId; }

    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }

    public String getColorCode() { return colorCode; }
    public void setColorCode(String colorCode) { this.colorCode = colorCode; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Set<Movie> getMovies() { return movies; }
    public void setMovies(Set<Movie> movies) { this.movies = movies; }

    // ==========================================
    // EQUALS, HASHCODE, TOSTRING
    // ==========================================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tmdbId=" + tmdbId +
                ", isActive=" + isActive +
                '}';
    }
}
