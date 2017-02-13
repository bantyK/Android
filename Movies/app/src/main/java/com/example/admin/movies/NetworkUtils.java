package com.example.admin.movies;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by Banty on 2/13/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    public static final String BASE_URL = "api.themoviedb.org";
    public static final String API_KEY = "b33cef0e8f9a26e8857366681a641e25";

    public URL formQueryURL(String movieName){
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .authority(BASE_URL)
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("api_key",API_KEY)
                .appendQueryParameter("query",movieName);

        String targetUrlString = uriBuilder.build().toString();
        Log.i(TAG, "formQueryURL: Movie URL : " + targetUrlString);

        URL targetUrl = null;

        try{
            targetUrl = new URL(targetUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return targetUrl;


    }
}
