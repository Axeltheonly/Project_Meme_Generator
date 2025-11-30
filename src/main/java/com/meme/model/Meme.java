package com.meme.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Meme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String filename; // stored file name
    private String originalFilename;
    private String topText;
    private String bottomText;
    private LocalDateTime createdAt;


    public Meme() {}


    public Meme(String filename, String originalFilename, String topText, String bottomText) {
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.topText = topText;
        this.bottomText = bottomText;
        this.createdAt = LocalDateTime.now();
    }


// getters and setters


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }


    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }


    public String getOriginalFilename() { return originalFilename; }
    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }


    public String getTopText() { return topText; }
    public void setTopText(String topText) { this.topText = topText; }


    public String getBottomText() { return bottomText; }
    public void setBottomText(String bottomText) { this.bottomText = bottomText; }


    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}