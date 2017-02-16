package com.example.admin.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by Banty on 2/14/2017.
 */

public class MovieDetailActivity extends AppCompatActivity implements DownloadTaskCompletedListener{
    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    private TextView movieTitleTextView;
    private ImageView movieThumbImageView;
    private int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: created");
        setContentView(R.layout.movie_detail_activity);

        movieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        movieThumbImageView = (ImageView) findViewById(R.id.iv_movie_detail_thumb);
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
        // TODO: 16/02/17 check for null movieDetail object while populating UI
        movieTitleTextView.setText(movieDetail.getTitle());
        URL thumbURL = new NetworkUtils().buildThumbURL(MovieSingleton.getInstance().getConfig(), movieDetail.getPosterPath());
        ImageLoader.loadImageWithGlide(this, thumbURL.toString(), movieThumbImageView);

    }
}
