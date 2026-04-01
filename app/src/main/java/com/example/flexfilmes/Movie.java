package com.example.flexfilmes;

// Modelo Movie
public class Movie {

    // Atributos
    private final String title;
    private final String description;
    private final int imageResId;

    // Construtor
    public Movie(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}