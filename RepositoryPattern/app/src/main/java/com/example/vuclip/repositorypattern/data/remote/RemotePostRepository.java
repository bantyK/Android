package com.example.vuclip.repositorypattern.data.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.example.vuclip.repositorypattern.data.PostDataRepository;
import com.example.vuclip.repositorypattern.model.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Banty on 08/04/18.
 */
public class RemotePostRepository implements PostDataRepository {

    private final static Map<String, Post> DUMMY_DATA;
    private static RemotePostRepository INSTANCE = null;

    static {
        DUMMY_DATA = new LinkedHashMap<>(2);

        addDummyPost("Why Android is so Awesome?");
        addDummyPost("Anger management");

    }

    private final int DELAY_IN_MILLIS = 2000;

    private RemotePostRepository() {
    }

    private static void addDummyPost(String title) {
        Post post = new Post(title);
        DUMMY_DATA.put(post.getId(), post);
    }

    public static RemotePostRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemotePostRepository();
        return INSTANCE;
    }

    @Override
    public void getPosts(@NotNull PostDataRepository.LoadPostsCallback callback) {
        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onPostLoaded(new ArrayList<>(DUMMY_DATA.values()));
            }
        }, DELAY_IN_MILLIS);
    }

    @Override
    public void getPost(@NotNull String postId, @NotNull GetPostCallback callback) {
        final Post post = DUMMY_DATA.get(postId);

        getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onPostLoaded(post);
            }
        }, DELAY_IN_MILLIS);
    }

    private Handler getHandler() {
        return new Handler();
    }


    @Override
    public void deletePost(@NonNull String postId) {
        DUMMY_DATA.remove(postId);
    }

    @Override
    public void deleteAllPost() {
        DUMMY_DATA.clear();
    }

    @Override
    public void savePost(@NonNull Post post) {
        DUMMY_DATA.put(post.getId(), post);
    }
}
