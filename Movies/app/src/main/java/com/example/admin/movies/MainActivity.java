package com.example.admin.movies;
/**
 * Created by Banty on 2/13/2017.
 *
 * Launcher Activity
 */
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadTaskCompletedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private NetworkUtils networkUtils;
    private ArrayList<Movie> movieList;

    //UI elements
    private EditText movieNameEditText;
    private Button findMovieButton;

    //setting the RecyclerView components
    private RecyclerView movieListRecyclerView;
    private String movieName;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter adapter;

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

    }

    @Override
    public void onTaskCompleted(ArrayList<Movie> movies) {
        Log.d(TAG, "onTaskCompleted: download task completed : " + movies.get(0).getTitle());
        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecyclerViewAdapter(MainActivity.this,movies);
        movieListRecyclerView.setLayoutManager(layoutManager);
        movieListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        //find the movie name that the user entered from the edittext
        movieName = movieNameEditText.getText().toString();

        //initiate the network utils class and building the target URL
        networkUtils = new NetworkUtils();
        URL targetURL = networkUtils.buildMoviesListURL(movieName);

        //start the async task for downloading the json from movies api
        MoviesDownloadParserTask downloadTask = new MoviesDownloadParserTask(MainActivity.this, this);
        downloadTask.execute(targetURL);
    }
}
