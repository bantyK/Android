package com.example.admin.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Banty on 2/14/2017.
 */

public class MovieDetailActivity extends AppCompatActivity implements DownloadTaskCompletedListener {
    private final static String TAG = MovieDetailActivity.class.getSimpleName();
    private TextView movieTitleTextView, movieDetailCertificate, movieDetailTagline, movieDetailRating, movieDetailBudget, movieDetailRevenue;
    private ImageView movieThumbImageView;
    private ExpandableListView expandableListView;
    private int movieId;
    private ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: created");
        setContentView(R.layout.movie_detail_activity);

        movieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        movieDetailCertificate = (TextView) findViewById(R.id.tv_movie_detail_certificate);
        movieDetailTagline = (TextView) findViewById(R.id.tv_movie_detail_tagline);
        movieDetailRating = (TextView) findViewById(R.id.tv_movie_detail_rating);
        movieDetailBudget = (TextView) findViewById(R.id.tv_movie_detail_budget);
        movieDetailRevenue = (TextView) findViewById(R.id.tv_movie_detail_revenue);
        movieThumbImageView = (ImageView) findViewById(R.id.iv_movie_detail_thumb);
        expandableListView = (ExpandableListView) findViewById(R.id.lv_expandable);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(IntentExtras.MOVIE_ID)) {
            movieId = intent.getExtras().getInt(IntentExtras.MOVIE_ID);
            URL movieDetailURL = new NetworkUtils().buildSingleMovieDetailURL(String.valueOf(movieId));
            Log.d(TAG, "onCreate: http## : " + movieDetailURL);
            DownloaderTask downloaderTask = new DownloaderTask(this, this);
            downloaderTask.execute(movieDetailURL);
        }
    }

    @Override
    public void onTaskCompleted(String jsonResponse) {
        JsonParser jsonParser = new JsonParser();
        MovieDetail movieDetail = jsonParser.parseMovieDetails(jsonResponse);
        // TODO: 16/02/17 check for null movieDetail object while populating UI
        if (movieDetail != null) {
            movieTitleTextView.setText(movieDetail.getTitle());
//            movieDetailCertificate.setText(movieDetail.isAdult());
            movieDetailTagline.setText(movieDetail.getTagline());
            movieDetailRating.setText(String.valueOf(movieDetail.getAverageRating()));

            if (movieDetail.getBudget().equals("0")) {
                movieDetailBudget.append(getString(R.string.not_available));
            } else {
                movieDetailBudget.append(String.valueOf(movieDetail.getBudget()));
            }

            if (movieDetail.getRevenue().equals("0")) {
                movieDetailRevenue.append(getString(R.string.not_available));
            } else {
                movieDetailRevenue.append(String.valueOf(movieDetail.getRevenue()));
            }


            URL thumbURL = new NetworkUtils().buildThumbURL(MovieSingleton.getInstance().getConfig(), movieDetail.getPosterPath());
            ImageLoader.loadImageWithGlide(this, thumbURL.toString(), movieThumbImageView);


            List<String> expandableListHeaders = new ArrayList<>();
            expandableListHeaders.add(getString(R.string.expandable_list_header_genre));
            expandableListHeaders.add(getString(R.string.expandable_list_header_prod_companies));
            HashMap<String, List<String>> expandableListChildItems = new HashMap<>();
            expandableListChildItems.put(expandableListHeaders.get(0), movieDetail.getGenres());
            expandableListChildItems.put(expandableListHeaders.get(1), movieDetail.getProductionCompanies());
            expandableListAdapter = new ExpandableListAdapter(this, expandableListHeaders, expandableListChildItems);
            expandableListView.setAdapter(expandableListAdapter);
        }

    }
}
