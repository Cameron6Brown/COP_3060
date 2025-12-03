package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Hobby Entity - Replaces temporary storage with persistent table
 * 
 * RELATIONSHIPS:
 * - MANY-TO-ONE: Hobby -> User (Many hobbies belong to one user)
 * 
 * @author Cameron Brown
 */
@Entity
@Table(name = "hobbies", indexes = {
    @Index(name = "idx_hobby_name", columnList = "name"),
    @Index(name = "idx_hobby_category", columnList = "category"),
    @Index(name = "idx_hobby_user", columnList = "user_id")
})
public class Hobby {

    // ==========================================
    // PRIMARY KEY
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================================
    // VALIDATED FIELDS
    // ==========================================
    @NotBlank(message = "Hobby name is required")
    @Size(min = 2, max = 100, message = "Hobby name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private HobbyCategory category;

    @Min(value = 1, message = "Skill level must be at least 1")
    @Max(value = 10, message = "Skill level cannot exceed 10")
    @Column(name = "skill_level")
    private Integer skillLevel;

    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 100, message = "Years of experience cannot exceed 100")
    @Column(name = "years_experience")
    private Integer yearsExperience;

    // ==========================================
    // TIMESTAMP FIELDS
    // ==========================================
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ==========================================
    // MANY-TO-ONE: Hobby -> User
    // Many hobbies can belong to one user
    // ==========================================
    @ManyToOne(fetch =
