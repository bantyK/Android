package com.example.admin.twitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 2/12/2017.
 */
public class ResponseParser {
    private ArrayList<TweetData> tweets;

    public ArrayList<TweetData> parse(String userTimelineJsonString) {
        tweets = new ArrayList<>();
        try {
            JSONArray rootArray = new JSONArray(userTimelineJsonString);
            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject tweet = rootArray.getJSONObject(i);
                String createdAt = tweet.getString("created_at");
                String text = tweet.getString("text");
                int retweetCount = tweet.getInt("retweet_count");
                int favouriteCount = tweet.getInt("favorite_count");

                tweets.add(new TweetData(new Date(createdAt),text,favouriteCount,retweetCount));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweets;
    }
}
