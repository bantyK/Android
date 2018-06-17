package com.example.vuclip.repositorypattern.data;

import com.example.vuclip.repositorypattern.model.Post;

import java.util.List;

/**
 * Created by Banty on 08/04/18.
 */
public interface PostDataRepository {

    interface LoadPostsCallback {

        void onPostLoaded(List<Post> posts);

        void onPostFailedToLoad();
    }

    interface GetPostCallback {

        void onPostLoaded(Post posts);

        void onPostFailedToLoad();
    }

    void getPosts(LoadPostsCallback callback);

    void getPost(String postId, GetPostCallback callback);

    void savePost(Post post);

    void deletePost(String postId);

    void deleteAllPost();
}
