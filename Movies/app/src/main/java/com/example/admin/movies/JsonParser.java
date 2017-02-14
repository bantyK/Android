package com.example.admin.movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Banty on 2/13/2017.
 * <p>
 * Parse the response json and creates a movie Arraylist containing movie details
 */

public class JsonParser {
    private String jsonString;
    private Movie movie;
    private int id;
    private String title;
    private String releaseDate;
    private String thumbUrl;
    private Double rating;
    private boolean isAdult;

    public JsonParser() {

    }

    public ArrayList<Movie> parseMovieList(String jsonString) {
        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject singleMovieJson;
        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONArray moviesArray = rootObject.getJSONArray("results");
            for (int index = 0; index < moviesArray.length(); index++) {
                singleMovieJson = moviesArray.getJSONObject(index);
                id = singleMovieJson.getInt("id");
                title = singleMovieJson.getString("title");
                releaseDate = singleMovieJson.getString("release_date");
                thumbUrl = singleMovieJson.getString("poster_path");
                rating = singleMovieJson.getDouble("vote_average");
                isAdult = singleMovieJson.getBoolean("adult");

                movie = new Movie(id, title, releaseDate,thumbUrl, rating, isAdult);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public Configuration getCongigParam(String jsonString) {
        String baseUrl = null, secureBaseUrl = null;
        ArrayList<String> imageSize = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(jsonString);
            JSONObject imageObject = rootObject.getJSONObject("images");

            baseUrl = imageObject.getString("base_url");
            secureBaseUrl = imageObject.getString("secure_base_url");
            JSONArray sizeArray = imageObject.getJSONArray("backdrop_sizes");

            for (int i = 0; i < sizeArray.length(); i++) {
                imageSize.add(sizeArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Configuration(baseUrl, secureBaseUrl, imageSize);

    }
}
