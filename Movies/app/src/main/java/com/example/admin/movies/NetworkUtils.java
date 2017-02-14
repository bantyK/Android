package com.example.admin.movies;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Banty on 2/13/2017.
 */

public class NetworkUtils {

    public static final String BASE_URL = "api.themoviedb.org";
    public static final String API_KEY = "b33cef0e8f9a26e8857366681a641e25";
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private URL targetUrl;

    public URL buildMoviesListURL(String movieName) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority(BASE_URL)
                .appendPath(HttpConstants.API_VERSION)
                .appendPath(HttpConstants.PATH_SEARCH)
                .appendPath(HttpConstants.PATH_MOVIE)
                .appendQueryParameter(HttpConstants.QUERY_API_KEY, API_KEY)
                .appendQueryParameter(HttpConstants.QUERY_QUERYPARAM, movieName);

        String targetUrlString = uriBuilder.build().toString();
        Log.i(TAG, "buildMoviesListURL: Movie URL : " + targetUrlString);

        try {
            targetUrl = new URL(targetUrlString);
        } catch (MalformedURLException e) {
            Log.d(TAG, "buildSingleMovieDetailURL: Exception while creating movie list URL");
            e.printStackTrace();
        }
        return targetUrl;
    }

    public URL buildSingleMovieDetailURL(String movieId) {

        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority(BASE_URL)
                .appendPath(HttpConstants.API_VERSION)
                .appendPath(HttpConstants.PATH_MOVIE)
                .appendPath(movieId)
                .appendQueryParameter(HttpConstants.QUERY_API_KEY, API_KEY)
                .appendQueryParameter(HttpConstants.QUERY_LANGUAGE, "en-US");

        String urlString = uriBuilder.build().toString();
        Log.i(TAG, "buildSingleMovieDetailURL: Movie detail URl : " + urlString);

        try{
            targetUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.d(TAG, "buildSingleMovieDetailURL: Exception while creating movie detail URL");
            e.printStackTrace();
        }
        return targetUrl;
    }
}
