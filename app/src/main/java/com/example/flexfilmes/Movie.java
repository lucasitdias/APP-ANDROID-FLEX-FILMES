package com.example.flexfilmes;

// Classe modelo que representa um filme
public class Movie {
    private final String title;
    private final String description;
    private final int imageResId;

    public Movie(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
}
