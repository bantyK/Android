package com.example.admin.movies;

/**
 * Created by Banty on 2/13/2017.
 *
 * Interface for the listeners who want to listen to the task complete event of async task.
 */
import java.util.ArrayList;

public interface DownloadTaskCompletedListener {
    void onTaskCompleted(ArrayList<Movie> movies);
}
