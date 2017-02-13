package com.example.admin.movies;

import java.util.ArrayList;

public interface DownloadTaskCompletedListener {
    void onTaskCompleted(ArrayList<Movie> movies);
}
