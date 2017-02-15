package com.example.admin.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by Banty on 2/14/2017.
 */

public class MovieDetailActivity extends AppCompatActivity implements DownloadTaskCompletedListener{
    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    private TextView movieTitleTextView;
    private int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        movieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(IntentExtras.MOVIE_ID)){
            movieId = intent.getExtras().getInt(IntentExtras.MOVIE_ID);
            URL movieDetailURL = new NetworkUtils().buildSingleMovieDetailURL(String.valueOf(movieId));
            DownloaderTask downloaderTask = new DownloaderTask(this,this);
            downloaderTask.execute(movieDetailURL);
        }
    }

    @Override
    public void onTaskCompleted(String jsonResponse) {
        JsonParser jsonParser = new JsonParser();
        MovieDetail movieDetail = jsonParser.parseMovieDetails(jsonResponse);
        movieTitleTextView.setText(movieDetail.toString());
    }
}
