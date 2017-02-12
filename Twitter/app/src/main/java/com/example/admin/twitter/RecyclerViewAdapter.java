package com.example.admin.twitter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 2/12/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<TweetData> tweets;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<TweetData> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(context)
                .inflate(R.layout.tweet_list_item,parent,false);

        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tweetTextView.setText(tweets.get(position).getTweetText());
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tweetTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            tweetTextView = (TextView) itemView.findViewById(R.id.tv_tweet_text);
         }
    }
}
