package com.example.admin.movies;

import android.app.Application;
import android.util.Log;

/*
    This is an application level class which is used only and only for storing data.

    There shouldn't be any logic here (only getters if at all necessary).
 */
public class MovieSingleton extends Application {
    private static final String TAG = MovieSingleton.class.getSimpleName();

    private static MovieSingleton applicationInstance;
    private Configuration config;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: application created");
        applicationInstance = this;
    }

    public static MovieSingleton getInstance() {
        if (applicationInstance == null) {
            RuntimeException runtimeException = new RuntimeException("Application not initialized");
            throw runtimeException;
        } else {
            return applicationInstance;
        }
    }

    public void setConfig(Configuration configuration) {
        config = configuration;
    }

    public Configuration getConfig() {
        return config;
    }
}
