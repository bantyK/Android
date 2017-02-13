package com.example.admin.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadTaskCompletedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NetworkUtils networkUtils;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String movieName = "The Avengers";

        //initiate the network utils class and building the target URL
        networkUtils = new NetworkUtils();
        URL targetURL = networkUtils.formQueryURL(movieName);

        //start the async task for downloading the json from movies api
        MoviesDownloadParserTask downloadTask = new MoviesDownloadParserTask(this,this);
        downloadTask.execute(targetURL);
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        movieList = movies;
        Log.d(TAG, "onTaskCompleted: download task completed : " + movies.size());
    }
}
