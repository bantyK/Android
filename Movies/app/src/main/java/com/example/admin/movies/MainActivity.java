package com.example.admin.movies;

import android.net.Network;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements DownloadTaskCompletedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String movieName = "The Avengers";
        URL targetURL = NetworkUtils.formQueryURL(movieName);
        MovieDetailsDownloadTask downloadTask = new MovieDetailsDownloadTask(this,this);
        downloadTask.execute(targetURL);
    }

    @Override
    public void onTaskCompleted(String jsonResponse) {
        Log.d(TAG, "onTaskCompleted: download task completed : " + jsonResponse);
    }
}
