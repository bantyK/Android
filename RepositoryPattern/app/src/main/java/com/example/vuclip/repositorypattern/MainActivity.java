package com.example.vuclip.repositorypattern;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.vuclip.repositorypattern.data.PostDataRepository;
import com.example.vuclip.repositorypattern.data.PostRepository;
import com.example.vuclip.repositorypattern.data.local.LocalPostRepository;
import com.example.vuclip.repositorypattern.data.local.PostDatabase;
import com.example.vuclip.repositorypattern.data.remote.RemotePostRepository;
import com.example.vuclip.repositorypattern.model.Post;
import com.example.vuclip.repositorypattern.utils.AppExecutors;

import java.util.List;

/**
 * Created by Banty on 08/04/18.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PostDatabase database = PostDatabase.getInstance(this);

        PostRepository repository = PostRepository.getInstance(
                RemotePostRepository.getInstance(),
                LocalPostRepository.getInstance(database.postDao(), new AppExecutors()));

        repository.getPosts(new PostDataRepository.LoadPostsCallback() {
            @Override
            public void onPostLoaded(List<Post> posts) {
                Log.d(TAG, "onPostLoaded: " + posts.size());
            }

            @Override
            public void onPostFailedToLoad() {
                Log.d(TAG, "onPostFailedToLoad: posts failed to load");
            }
        });
    }
}
