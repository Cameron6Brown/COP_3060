package com.cameron.cop3060.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO for TMDB Movie API response
 * Maps JSON response from The Movie Database API
 * 
 * @author Cameron Brown
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBMovieDTO {

    private Long id;
    private String title;

    @JsonProperty("original_title")
    private String originalTitle;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private Double popularity;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    // Full genre objects (only in detailed response)
    private List<GenreDTO> genres;

    @JsonProperty("original_language")
    private String originalLanguage;

    private Boolean adult;
    private Boolean video;

    // Detailed response fields
    private Integer runtime;
    private Long budget;
    private Long revenue;
    private String tagline;
    private String status;
    private String homepage;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("production_companies")
    private List<ProductionCompanyDTO> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountryDTO> productionCountries;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguageDTO> spokenLanguages;

    // Credits (if appended)
    private CreditsDTO credits;

    // ==========================================
    // NESTED DTO CLASSES
    // ==========================================
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GenreDTO {
        private Integer id;
        private String name;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductionCompanyDTO {
        private Integer id;
        private String name;
        @JsonProperty("logo_path")
        private String logoPath;
        @JsonProperty("origin_country")
        private String originCountry;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getLogoPath() { return logoPath; }
        public void setLogoPath(String logoPath) { this.logoPath = logoPath; }
        public String getOriginCountry() { return originCountry; }
        public void setOriginCountry(String originCountry) { this.originCountry = originCountry; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductionCountryDTO {
        @JsonProperty("iso_3166_1")
        private String iso31661;
        private String name;

        public String getIso31661() { return iso31661; }
        public void setIso31661(String iso31661) { this.iso31661 = iso31661; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SpokenLanguageDTO {
        @JsonProperty("iso_639_1")
        private String iso6391;
        private String name;
        @JsonProperty("english_name")
        private String englishName;

        public String getIso6391() { return iso6391; }
        public void setIso6391(String iso6391) { this.iso6391 = iso6391; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEnglishName() { return englishName; }
        public void setEnglishName(String englishName) { this.englishName = englishName; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CreditsDTO {
        private List<CastDTO> cast;
        private List<CrewDTO> crew;

        public List<CastDTO> getCast() { return cast; }
        public void setCast(List<CastDTO> cast) { this.cast = cast; }
        public List<CrewDTO> getCrew() { return crew; }
        public void setCrew(List<CrewDTO> crew) { this.crew = crew; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CastDTO {
        private Integer id;
        private String name;
        private String character;
        @JsonProperty("profile_path")
        private String profilePath;
        private Integer order;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCharacter() { return character; }
        public void setCharacter(String character) { this.character = character; }
        public String getProfilePath() { return profilePath; }
        public void setProfilePath(String profilePath) { this.profilePath = profilePath; }
        public Integer getOrder() { return order; }
        public void setOrder(Integer order) { this.order = order; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CrewDTO {
        private Integer id;
        private String name;
        private String job;
        private String department;
        @JsonProperty("profile_path")
        private String profilePath;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getJob() { return job; }
        public void setJob(String job) { this.job = job; }
        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }
        public String getProfilePath() { return profilePath; }
        public void setProfilePath(String profilePath) { this.profilePath = profilePath; }
    }

    // ==========================================
    // CONSTRUCTORS
    // ==========================================
    public TMDBMovieDTO() {}

    // ==========================================
    // UTILITY METHODS
    // ==========================================
    
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
    public String get
