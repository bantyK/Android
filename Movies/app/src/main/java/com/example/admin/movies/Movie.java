package com.example.admin.movies;

/**
 * Created by Banty on 2/13/2017.
 * <p>
 * This is a simple POJO to represent a movie.
 */

public class Movie {
    private int id;
    private String title;
    private String overview;
    private String thumbUrl;
    private double rating;
    private boolean isAdult;

    public Movie(int id, String title, String overview, String thumbUrl, double rating, boolean isAdult) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.thumbUrl = thumbUrl;
        this.rating = rating;
        this.isAdult = isAdult;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public double getRating() {
        return rating;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getOverview() {
        return overview;
    }
}
