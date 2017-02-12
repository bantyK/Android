package com.example.admin.twitter;

import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2017.
 */

public interface AsyncTaskCompletedListener {
    void passDateFromASync(ArrayList<TweetData> tweets);
}
