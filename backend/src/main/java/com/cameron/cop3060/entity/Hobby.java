package com.cameron.cop3060.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hobbies")
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hobby name is required")
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String name;

    @Size(max = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // MANY-TO-ONE: Hobby -> User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public enum Category {
        SPORTS, ARTS, TECHNOLOGY, OUTDOOR, INDOOR, SOCIAL, OTHER
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors
    public Hobby() {}

    public Hobby(String name) {
        this.name = name;
    }

    public Hobby(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
