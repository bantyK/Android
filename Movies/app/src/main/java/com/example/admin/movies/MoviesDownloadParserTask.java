package com.example.admin.movies;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Banty on 2/13/2017.
 *
 * Starts a background process for downloading and parsing the json from the api
 */

public class MoviesDownloadParserTask extends AsyncTask<URL, Void, String> {
    private static final String TAG = MoviesDownloadParserTask.class.getSimpleName();
    private Context mContext;
    private DownloadTaskCompletedListener taskCompletedListener;
    private ProgressDialog progressDialog;

    public MoviesDownloadParserTask(Context mContext, DownloadTaskCompletedListener listener) {
        this.mContext = mContext;
        this.taskCompletedListener = (DownloadTaskCompletedListener) listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(mContext.getString(R.string.getting_movie_list));
        progressDialog.show();

    }

    @Override
    protected String doInBackground(URL... urls) {
        URL targetURL = urls[0];
        String responseLine = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (targetURL != null) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) targetURL.openConnection();
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
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

        //call the parser class to get the movies arraylist
        ArrayList<Movie> movies = new JsonParser(responseJson).parse();

        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
        //notify mainActivity with the arraylist when task completed
        taskCompletedListener.onTaskCompleted(movies);
    }
}
