package com.example.admin.twitter;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 2/12/2017.
 */

public class DownloadTwitterTimelineTask extends AsyncTask<String,Void,String> {
    private final static String TAG = DownloadTwitterTimelineTask.class.getSimpleName();

    final static String CONSUMER_KEY = "tjQNmFNzssv7PJpsQmqEN65bi";
    final static String CONSUMER_SECRET = "wNvUDvWYH0uGPxK32ta8xcThE8NS4GzrjAsbYHa82yjvt3l3WK";
    final static String TwitterTokenURLString = "https://api.twitter.com/oauth2/token";
    final static String TwitterStreamURLString = "https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=";

    private AsyncTaskCompletedListener taskCompletedListener;
    @Override
    protected String doInBackground(String... params) {
        String result = null;

        if (params.length > 0) {
            result = getTwitterStream(params[0]);
        } else {
            result = null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(String userTimelineJsonString) {
        super.onPostExecute(userTimelineJsonString);
        System.out.println(userTimelineJsonString);
        ResponseParser parser = new ResponseParser();
        ArrayList<TweetData> tweets = parser.parse(userTimelineJsonString);
        taskCompletedListener.passDateFromASync(tweets);
    }

    private String getTwitterStream(String screenName) {
        String result = null;
        try {
            //Step 1 : URL Encode the consumer and secret key
            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String urlSecretKey = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");
            String apiKeySecretKeyCombined = urlApiKey + ":" + urlSecretKey;

            String base64Encoded = Base64.encodeToString(apiKeySecretKeyCombined.getBytes(), Base64.NO_WRAP);

            //Step 2 : Obtain a bearer token
            HttpPost httpPost = new HttpPost(TwitterTokenURLString);
            httpPost.setHeader("Authorization", "Basic " + base64Encoded);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
            String rawAuthorization = getResponseBody(httpPost);
            Authenticated auth = jsonToAuthenticated(rawAuthorization);

            if (auth != null && auth.token_type.equals("bearer")) {

                // Step 3: Authenticate API requests with bearer token
                HttpGet httpGet = new HttpGet(TwitterStreamURLString + screenName);
                // construct a normal HTTPS request and include an Authorization
                // header with the value of Bearer <>
                httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                httpGet.setHeader("Content-Type", "application/json");
                // update the results with the body of the response
                result = getResponseBody(httpGet);
                Log.i(TAG, "getTwitterStream: " + httpGet.getURI());
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getResponseBody(HttpRequestBase request) {
        StringBuilder sb = new StringBuilder();
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String reason = response.getStatusLine().getReasonPhrase();

            if (statusCode == 200) {

                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();

                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                sb.append(reason);
            }
        } catch (UnsupportedEncodingException ex) {
        } catch (ClientProtocolException ex1) {
        } catch (IOException ex2) {
        }
        return sb.toString();
    }

    private Authenticated jsonToAuthenticated(String rawAuthorization) {
        Authenticated auth = null;
        if (rawAuthorization != null && rawAuthorization.length() > 0) {
            try {
                Gson gson = new Gson();
                auth = gson.fromJson(rawAuthorization, Authenticated.class);
            } catch (IllegalStateException ex) {
                // just eat the exception
            }
        }
        return auth;
    }

    public void setListener(MainActivity mainActivity) {
        this.taskCompletedListener = mainActivity;
    }
}
