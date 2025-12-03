package com.cameron.cop3060.repository;

import com.cameron.cop3060.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreIgnoreCase(String genre);
    
    List<Movie> findByYearReleased(Integer year);
    
    List<Movie> findByYearReleasedBetween(Integer startYear, Integer endYear);
    
    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    List<Movie> findByRatingGreaterThanEqual(Double rating);
    
    @Query("SELECT DISTINCT m.genre FROM Movie m ORDER BY m.genre")
    List<String> findAllGenres();
    
    List<Movie> findAllByOrderByTitleAsc();
    
    List<Movie> findAllByOrderByYearReleasedDesc();
}
