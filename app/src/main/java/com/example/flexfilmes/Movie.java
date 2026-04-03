package com.example.flexfilmes;

// Modelo Movie atualizado: inclui genre e mantém compatibilidade com construtores antigos.
public class Movie {

    // Atributos
    private final String title;
    private final String description;
    private final String genre;       // novo campo
    private final int year;           // já existia
    private final int imageResId;     // id do drawable
    private final String ageRating;   // faixa etária (opcional)

    // flag para indicar se o poster é claro (overlay)
    private boolean posterLight = false;

    // Construtor completo (recomendado)
    public Movie(String title, String description, String genre, int year, int imageResId, String ageRating) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.year = year;
        this.imageResId = imageResId;
        this.ageRating = ageRating;
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

    // getter e setter para posterLight
    public boolean isPosterLight() { return posterLight; }
    public void setPosterLight(boolean posterLight) { this.posterLight = posterLight; }
}
