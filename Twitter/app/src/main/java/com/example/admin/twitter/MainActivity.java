package com.example.admin.twitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AsyncTaskCompletedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<TweetData> tweets;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_tweet_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DownloadTwitterTimelineTask downloadTwitterTimelineTask = new DownloadTwitterTimelineTask();
        downloadTwitterTimelineTask.setListener(this);
        downloadTwitterTimelineTask.execute("banty_panda");
    }

    @Override
    public void passDateFromASync(ArrayList<TweetData> tweets) {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(MainActivity.this,tweets);
        recyclerView.setAdapter(adapter);
    }
}
