package com.example.admin.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadTaskCompletedListener,View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NetworkUtils networkUtils;
    private ArrayList<Movie> movieList;

    private EditText movieNameEditText;
    private Button findMovieButton;
    private RecyclerView movieListRecyclerView;
    private String movieName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the references of the UI elements
        movieNameEditText = (EditText) findViewById(R.id.et_movie_name);
        findMovieButton = (Button) findViewById(R.id.btn_find_movie);
        movieListRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_list);

        //register the onClick listener of the button
        findMovieButton.setOnClickListener(this);

        //initiate the network utils class and building the target URL
        networkUtils = new NetworkUtils();
        URL targetURL = networkUtils.formQueryURL(movieName);

        //start the async task for downloading the json from movies api
        MoviesDownloadParserTask downloadTask = new MoviesDownloadParserTask(this, this);
        downloadTask.execute(targetURL);
    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        movieList = movies;
        Log.d(TAG, "onTaskCompleted: download task completed : " + movies.size());
    }

    @Override
    public void onClick(View v) {

    }
}
