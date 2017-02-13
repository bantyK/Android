package com.example.admin.movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Banty on 2/13/2017.
 * <p>
 * Parse the response json and creates a movie arraylist containing movie details
 */

public class JsonParser {
    private String jsonString;
    private Movie movie;
    private int id;
    private String title;
    private String overview;
    private String thumbUrl;
    private Double rating;
    private boolean isAdult;

    public JsonParser(String jsonString) {
        this.jsonString = jsonString;
    }

    public ArrayList<Movie> parse() {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject singleMovieJson;
        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray moviesArray = rootObject.getJSONArray("results");
            for (int index = 0; index < moviesArray.length(); index++) {
                singleMovieJson = moviesArray.getJSONObject(index);
                id = singleMovieJson.getInt("id");
                title = singleMovieJson.getString("title");
                overview = singleMovieJson.getString("overview");
                thumbUrl = singleMovieJson.getString("poster_path");
                rating = singleMovieJson.getDouble("vote_average");
                isAdult = singleMovieJson.getBoolean("adult");

                movie = new Movie(id, title, overview, thumbUrl, rating, isAdult);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
