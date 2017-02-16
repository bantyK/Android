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

                movie = new Movie(id, title, releaseDate, thumbUrl, rating, isAdult);
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

    public MovieDetail parseMovieDetails(String jsonResponse) {
        String title = null;
        String tagline = null;
        boolean isAdult = false;
        String budget = null;
        ArrayList<String> genres = null; // one movie can belong to more than one genre eg. Action and Adventure
        String imdbId = null;
        String overview = null;
        ArrayList<String> productionCompanies = null;
        String revenue = null;
        int runtime = 0;
        Double averageRating = null;
        String posterPath = null;
        try {
            JSONObject rootObject = new JSONObject(jsonResponse);

            if (rootObject.getString("original_title") != null)
                title = rootObject.getString("original_title");
            if (rootObject.getString("tagline") != null)
                tagline = rootObject.getString("tagline");
            isAdult = rootObject.getBoolean("adult");
            if (rootObject.getString("budget") != null)
                budget = String.valueOf(rootObject.get("budget"));
            if (rootObject.getJSONArray("genres") != null) {
                JSONArray genreArray = rootObject.getJSONArray("genres");
                genres = new ArrayList<>();
                for (int i = 0; i < genreArray.length(); i++) {
                    String genre = genreArray.getJSONObject(i).getString("name");
                    genres.add(genre);
                }
            }
            if (rootObject.getString("imdb_id") != null)
                imdbId = rootObject.getString("imdb_id");
            if (rootObject.getString("overview") != null)
                overview = rootObject.getString("overview");

            if (rootObject.getString("poster_path") != null)
                posterPath = rootObject.getString("poster_path");

            if (rootObject.getJSONArray("production_companies") != null) {
                productionCompanies = new ArrayList<>();
                JSONArray productionCompaniesArray = rootObject.getJSONArray("production_companies");
                for (int i = 0; i < productionCompaniesArray.length(); i++) {
                    String productionCompany = productionCompaniesArray.getJSONObject(i).getString("name");
                    productionCompanies.add(productionCompany);
                }
            }

            if (rootObject.get("revenue") != null)
                revenue = String.valueOf(rootObject.get("revenue"));

            runtime = rootObject.getInt("runtime");

            rating = rootObject.getDouble("vote_average");

            return new MovieDetail(title, tagline, isAdult, budget, genres, imdbId, overview, productionCompanies, revenue, runtime, rating, posterPath);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
