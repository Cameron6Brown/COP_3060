package com.cameron.cop3060.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO for TMDB Search API response wrapper
 * 
 * @author Cameron Brown
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBSearchResponseDTO {

    private Integer page;

    @JsonProperty("total_pages")
    private Integer totalPages;
