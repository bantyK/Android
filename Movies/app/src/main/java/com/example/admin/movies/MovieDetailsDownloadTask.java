package com.example.admin.movies;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Banty on 2/13/2017.
 */

public class MovieDetailsDownloadTask extends AsyncTask<URL,Void,String> {
    private static final String TAG = MovieDetailsDownloadTask.class.getSimpleName();
    private Context mContext;
    private DownloadTaskCompletedListener taskCompletedListener;

    public MovieDetailsDownloadTask(Context mContext, DownloadTaskCompletedListener listener) {
        this.mContext = mContext;
        this.taskCompletedListener = (DownloadTaskCompletedListener) listener;
    }


    @Override
    protected String doInBackground(URL... urls) {
        URL targetURL = urls[0];
        String responseLine = null;
        StringBuilder stringBuilder = new StringBuilder();
        if(targetURL != null){
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) targetURL.openConnection();
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if(responseCode == 200) {
                    InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    while ((responseLine = reader.readLine()) != null) {
                        stringBuilder.append(responseLine);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String responseJson) {
        super.onPostExecute(responseJson);
        Log.i(TAG, "onPostExecute: json response = " + responseJson);
        taskCompletedListener.onTaskCompleted(responseJson);
    }
}
