package com.example.flexfilmes;

// Modelo Movie
public class Movie {

    // Atributos
    private final String title;
    private final String description;
    private final int imageResId;
    private final int year;
    private final String ageRating;

    // Construtor completo
    public Movie(String title, String description, int imageResId, int year, String ageRating) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.year = year;
        this.ageRating = ageRating;
    }

    // Construtor antigo (compatibilidade)
    public Movie(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.year = 2024;
        this.ageRating = "14+";
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public int getYear() { return year; }
    public String getAgeRating() { return ageRating; }
}