package com.example.admin.movies;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Banty on 2/14/2017.
 */

public class MovieDetail {
    private String title;
    private String tagline;
    private boolean isAdult;
    private String budget;
    private ArrayList<String> genres; // one movie can belong to more than one genre eg. Action and Adventure
    private String imdbId;
    private String overview;
    private ArrayList<String> productionCompanies;
    private String revenue;
    private int runtime;
    private Double averageRating;
    private String posterPath;

    public MovieDetail(String title, String tagline, boolean isAdult, String budget, ArrayList<String> genres, String imdbId, String overview, ArrayList<String> productionCompanies, String revenue, int runtime, Double averageRating, String posterPath) {
        Log.d("Banty", "MovieDetail: " + title);
        this.title = title;
        this.tagline = tagline;
        this.isAdult = isAdult;
        this.budget = budget;
        this.genres = genres;
        this.imdbId = imdbId;
        this.overview = overview;
        this.productionCompanies = productionCompanies;
        this.revenue = revenue;
        this.runtime = runtime;
        this.averageRating = averageRating;
        this.posterPath = posterPath;
    }

    //Cast and crew will be handled in a different class, this class will use an object of that class


    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getBudget() {
        return budget;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOverview() {
        return overview;
    }

    public ArrayList<String> getProductionCompanies() {
        return productionCompanies;
    }

    public String getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public String getPosterPath() {
        return posterPath;
    }

}
