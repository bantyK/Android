package com.example.vuclip.repositorypattern.data;

import com.example.vuclip.repositorypattern.model.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Banty on 08/04/18.
 */
public class PostRepository implements PostDataRepository {
    private static PostRepository INSTANCE = null;

    private final PostDataRepository remoteDataRepository;
    private final PostDataRepository localDataRepository;

    Map<String, Post> cachedPosts;

    private boolean isCacheDirty = false;

    private PostRepository(PostDataRepository remoteDataRepository,
                           PostDataRepository localDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        this.localDataRepository = localDataRepository;
    }

    public static PostRepository getInstance(PostDataRepository remoteDataRepository,
                                       PostDataRepository localDataRepository) {

        if (INSTANCE == null) {
            INSTANCE = new PostRepository(remoteDataRepository, localDataRepository);
        }

        return INSTANCE;
    }

    @Override
    public void getPosts(final @NotNull LoadPostsCallback callback) {
        if (cachedPosts != null && !isCacheDirty) {
            callback.onPostLoaded(new ArrayList(cachedPosts.values()));
            return;
        }

        if (isCacheDirty) {
            // cache is expired, fetch fresh data from server.
            getPostFromRemoteDataSource(callback);
        } else {
            //cache is not expired, fetch the data from local storage
            localDataRepository.getPosts(new LoadPostsCallback() {
                @Override
                public void onPostLoaded(@NotNull List<Post> posts) {
                    refreshCache(posts);
                    callback.onPostLoaded(new ArrayList<Post>(cachedPosts.values()));
                }

                @Override
                public void onPostFailedToLoad() {
                    getPostFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void getPostFromRemoteDataSource(LoadPostsCallback callback) {
        remoteDataRepository.getPosts(new LoadPostsCallback() {
            @Override
            public void onPostLoaded(List<Post> posts) {
                refreshCache(posts);
                refreshLocalStorage(posts);
                callback.onPostLoaded(posts);
            }

            @Override
            public void onPostFailedToLoad() {
                callback.onPostFailedToLoad();
            }
        });
    }

    private void refreshLocalStorage(List<Post> posts) {
        localDataRepository.deleteAllPost();
        posts.forEach(localDataRepository::savePost);
    }

    /**
     * Refresh the post in the cache
     */
    private void refreshCache(List<Post> posts) {
        if (cachedPosts == null)
            cachedPosts = new LinkedHashMap<>();

        cachedPosts.clear();

        posts.forEach(post -> cachedPosts.put(post.getId(), post));
    }

    @Override
    public void getPost(@NotNull String postId, @NotNull final GetPostCallback callback) {
        checkNotNull(postId);
        checkNotNull(callback);

        Post cachedPost = getPostWithId(postId);

        //Respond immediately if post is available in the cache
        if(cachedPost != null) {
            callback.onPostLoaded(cachedPost);
            return;
        }

        //check local storage, if post is not available in cache
        localDataRepository.getPost(postId, new GetPostCallback() {
            @Override
            public void onPostLoaded(Post post) {
                refreshPostInCache(post);
                callback.onPostLoaded(post);
            }

            @Override
            public void onPostFailedToLoad() {
                // post not available locally also, fetch it from server
                remoteDataRepository.getPost(postId, new GetPostCallback() {
                    @Override
                    public void onPostLoaded(@NotNull Post post) {
                        refreshPostInCache(post);
                        callback.onPostLoaded(post);
                    }

                    @Override
                    public void onPostFailedToLoad() {
                        //post not available in cache, local and remote
                        callback.onPostFailedToLoad();
                    }
                });
            }
        });
    }

    private void refreshPostInCache(Post post) {
        if(cachedPosts == null)
            cachedPosts = new LinkedHashMap<>();

        cachedPosts.put(post.getId(), post);

    }

    private Post getPostWithId(String postId) {
        if(cachedPosts == null || cachedPosts.isEmpty())
            return null;

        return cachedPosts.get(postId);
    }

    @Override
    public void savePost(@NotNull Post post) {
        checkNotNull(post);
        remoteDataRepository.savePost(post);
        localDataRepository.savePost(post);
        refreshPostInCache(post);
    }

    @Override
    public void deletePost(@NotNull String postId) {
        remoteDataRepository.deletePost(postId);
        localDataRepository.deletePost(postId);

        cachedPosts.remove(postId);
    }

    @Override
    public void deleteAllPost() {
        remoteDataRepository.deleteAllPost();
        localDataRepository.deleteAllPost();

        if(cachedPosts != null) cachedPosts.clear();
    }
}
