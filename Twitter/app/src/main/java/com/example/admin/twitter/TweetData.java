package com.example.admin.twitter;

import java.util.Date;

/**
 * Created by Admin on 2/12/2017.
 */

public class TweetData {
    private Date createdAt;
    private String text;
    private int favouriteCount;
    private int retweetCount;

    public TweetData(Date createdAt, String text, int favouriteCount, int retweetCount) {
        this.createdAt = createdAt;
        this.text = text;
        this.favouriteCount = favouriteCount;
        this.retweetCount = retweetCount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getTweetText() {
        return text;
    }

    public int getFavouriteCount() {
        return favouriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }
}
