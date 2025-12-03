package com.cameron.cop3060.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * ExternalMovieCache Entity - Caches movie data from external APIs
 * Stores data from TMDB API to reduce API calls and improve performance
 * 
 * @author Cameron Brown
 */
@Entity
@Table(name = "external_movie_cache", indexes = {
    @Index(name = "idx_cache_tmdb_id", columnList = "tmdb_id", unique = true),
    @Index(name = "idx_cache_title", columnList = "title"),
    @Index(name = "idx_cache_expires", columnList = "expires_at"),
    @Index(name = "idx_cache_source", columnList = "source")
})
public class ExternalMovieCache {

    // ==========================================
    // PRIMARY KEY
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // EXTERNAL API FIELDS
    // ==========================================
    @NotNull(message = "TMDB ID is required")
    @Column(name = "tmdb_id", nullable = false, unique = true)
    private Long tmdbId;

    @NotBlank(message = "Title is required")
    @Size(max = 300, message = "Title cannot exceed 300 characters")
    @Column(nullable = false, length = 300)
    private String title;

    @Size(max = 300, message = "Original title cannot exceed 300 characters")
    @Column(name = "original_title", length = 300)
    private String originalTitle;

    @Column(length = 5000)
    private String overview;

    @Size(max = 500, message = "Poster path cannot exceed 500 characters")
    @Column(name = "poster_path", length = 500)
    private String posterPath;

    @Size(max = 500, message = "Backdrop path cannot exceed 500 characters")
    @Column(name = "backdrop_path", length = 500)
    private String backdropPath;

    @Column(name = "release_date", length = 20)
    private String releaseDate;

    @Column(name = "release_year")
    private Integer releaseYear;

    @DecimalMin(value = "0.0", message = "Vote average must be at least 0.0")
    @DecimalMax(value = "10.0", message = "Vote average cannot exceed 10.0")
    @Column(name = "vote_average")
    private Double voteAverage;

    @Min(value = 0, message = "Vote count cannot be negative")
    @Column(name = "vote_count")
    private Integer voteCount;

    @Column(precision = 10, scale = 3)
    private Double popularity;

    @Column(name = "genre_ids", length = 200)
    private String genreIds; // Stored as comma-separated values

    @Size(max = 10, message = "Language code cannot exceed 10 characters")
    @Column(name = "original_language", length = 10)
    private String originalLanguage;

    @Column(name = "is_adult")
    private Boolean isAdult = false;

    @Min(value = 0, message = "Runtime cannot be negative")
    @Column(name = "runtime_minutes")
    private Integer runtimeMinutes;

    @Size(max = 200, message = "Director cannot exceed 200 characters")
    @Column(length = 200)
    private String director;

    // Budget and revenue (in USD)
    @Min(value = 0, message = "Budget cannot be negative")
    private Long budget;

    @Min(value = 0, message = "Revenue cannot be negative")
    private Long revenue;

    @Size(max = 500, message = "Tagline cannot exceed 500 characters")
    @Column(length = 500)
    private String tagline;

    @Size(max = 100, message = "Status cannot exceed 100 characters")
    @Column(length = 100)
    private String status; // Released, Post Production, etc.

    // ==========================================
    // CACHE METADATA
    // ==========================================
    @NotBlank(message = "Source is required")
    @Size(max = 50, message = "Source cannot exceed 50 characters")
    @Column(nullable = false, length = 50)
    private String source = "TMDB"; // API source

    @Column(name = "api_response", columnDefinition = "TEXT")
    private String apiResponse; // Raw JSON response for debugging

    @Column(name = "last_fetched", nullable = false)
    private LocalDateTime lastFetched;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "fetch_count")
    private Integer fetchCount = 1;

    @Column(name = "is_detailed")
    private Boolean isDetailed = false; // Whether full details were fetched

    // ==========================================
    // TIMESTAMP FIELDS
    // ==========================================
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ==========================================
    // LIFECYCLE CALLBACKS
    // ==========================================
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.lastFetched = LocalDateTime.now();
        if (this.expiresAt == null) {
            this.expiresAt = LocalDateTime.now().plusHours(24); // Default 24-hour cache
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    public ExternalMovieCache() {}

    public ExternalMovieCache(Long tmdbId, String title) {
        this.tmdbId = tmdbId;
        this.title = title;
    }

    public ExternalMovieCache(Long tmdbId, String title, String overview, Double voteAverage) {
        this.tmdbId = tmdbId;
        this.title = title;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    // ==========================================
    // UTILITY METHODS
    // ==========================================
    
    /**
     * Check if cache has expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Refresh the cache expiration
     */
    public void refreshCache(int hoursToLive) {
        this.lastFetched = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusHours(hoursToLive);
        this.fetchCount = (this.fetchCount != null) ? this.fetchCount + 1 : 1;
    }

    /**
     * Get full poster URL
     */
    public String getFullPosterUrl(String baseUrl) {
        return (posterPath != null && !posterPath.isEmpty()) 
            ? baseUrl + posterPath 
            : null;
    }

    /**
     * Get full backdrop URL
     */
    public String getFullBackdropUrl(String baseUrl) {
        return (backdropPath != null && !backdropPath.isEmpty()) 
            ? baseUrl + backdropPath 
            : null;
    }

    /**
     * Parse genre IDs from comma-separated string
     */
    public int[] getGenreIdsArray() {
        if (genreIds == null || genreIds.isEmpty()) {
            return new int[0];
        }
        String[] parts = genreIds.split(",");
        int[] ids = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try {
                ids[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                ids[i] = 0;
            }
        }
        return ids;
    }

    /**
     * Set genre IDs from array
     */
    public void setGenreIdsFromArray(int[] ids) {
        if (ids == null || ids.length == 0) {
            this.genreIds = null;
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(ids[i]);
        }
        this.genreIds = sb.toString();
    }

    // ==========================================
    // GETTERS AND SETTERS
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getTmdbId() { return tmdbId; }
    public void setTmdbId(Long tmdbId) { this.tmdbId = tmdbId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOriginalTitle() { return originalTitle; }
    public void setOriginalTitle(String originalTitle) { this.originalTitle = originalTitle; }

    public String getOverview() { return overview; }
    public void setOverview(String overview) { this.overview = overview; }

    public String getPosterPath() { return posterPath; }
    public void setPosterPath(String posterPath) { this.posterPath = posterPath; }

    public String getBackdropPath() { return backdropPath; }
    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public Double getVoteAverage() { return voteAverage; }
    public void setVoteAverage(Double voteAverage) { this.voteAverage = voteAverage; }

    public Integer getVoteCount() { return voteCount; }
    public void setVoteCount(Integer voteCount) { this.voteCount = voteCount; }

    public Double getPopularity() { return popularity; }
    public void setPopularity(Double popularity) { this.popularity = popularity; }

    public String getGenreIds() { return genreIds; }
    public void setGenreIds(String genreIds) { this.genreIds = genreIds; }

    public String getOriginalLanguage() { return originalLanguage; }
    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }

    public Boolean getIsAdult() { return isAdult; }
    public void setIsAdult(Boolean isAdult) { this.isAdult = isAdult; }

    public Integer getRuntimeMinutes() { return runtimeMinutes; }
    public void setRuntimeMinutes(Integer runtimeMinutes) { this.runtimeMinutes = runtimeMinutes; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Long getBudget() { return budget; }
    public void setBudget(Long budget) { this.budget = budget; }

    public Long getRevenue() { return revenue; }
    public void setRevenue(Long revenue) { this.revenue = revenue; }

    public String getTagline() { return tagline; }
    public void setTagline(String tagline) { this.tagline = tagline; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getApiResponse() { return apiResponse; }
    public void setApiResponse(String apiResponse) { this.apiResponse = apiResponse; }

    public LocalDateTime getLastFetched() { return lastFetched; }
    public void setLastFetched(LocalDateTime lastFetched) { this.lastFetched = lastFetched; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public Integer getFetchCount() { return fetchCount; }
    public void setFetchCount(Integer fetchCount) { this.fetchCount = fetchCount; }

    public Boolean getIsDetailed() { return isDetailed; }
    public void setIsDetailed(Boolean isDetailed) { this.isDetailed = isDetailed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public String toString() {
        return "ExternalMovieCache{" +
                "id=" + id +
                ", tmdbId=" + tmdbId +
                ", title='" + title + '\'' +
                ", voteAverage=" + voteAverage +
                ", isExpired=" + isExpired() +
                '}';
    }
}
