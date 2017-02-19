package com.example.admin.movies;

/**
 * Created by Banty on 2/13/2017.
 * <p>
 * This is a simple POJO to represent a movie.
 */

public class Movie {
    private int id;
    private String title;
    private String releaseDate;

    public Movie(int id, String title, String releaseDate) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
