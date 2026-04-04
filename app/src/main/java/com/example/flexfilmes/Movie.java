package com.example.flexfilmes;

/**
 * Modelo Movie atualizado: inclui genre, ageRating e flag posterLight.
 * Mantém compatibilidade com construtores antigos usados no projeto.
 */
public class Movie {

    private final String title;
    private final String description;
    private final String genre;
    private final int year;
    private final int imageResId;
    private final String ageRating;

    // Flag para controlar overlay em posters claros
    private boolean posterLight = false;

    // Construtor completo
    public Movie(String title, String description, String genre, int year, int imageResId, String ageRating) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.imageResId = imageResId;
        this.ageRating = ageRating != null ? ageRating : "+14";
    }

    // Construtor usado em CatalogActivity (sem ageRating)
    public Movie(String title, String description, String genre, int year, int imageResId) {
        this(title, description, genre, year, imageResId, "+14");
    }

    // Construtor antigo (compatibilidade: title, description, image)
    public Movie(String title, String description, int imageResId) {
        this(title, description, "Gênero desconhecido", 2024, imageResId, "+14");
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public int getImageResId() { return imageResId; }
    public String getAgeRating() { return ageRating; }

    // posterLight
    public boolean isPosterLight() { return posterLight; }
    public void setPosterLight(boolean posterLight) { this.posterLight = posterLight; }
}
