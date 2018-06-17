package com.example.vuclip.repositorypattern.data.local;

import android.support.annotation.NonNull;

import com.example.vuclip.repositorypattern.data.PostDataRepository;
import com.example.vuclip.repositorypattern.model.Post;
import com.example.vuclip.repositorypattern.utils.AppExecutors;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Banty on 08/04/18.
 */
public class LocalPostRepository implements PostDataRepository {

    private static volatile LocalPostRepository INSTANCE = null;

    private AppExecutors mAppExecutors;
    private PostDao mPostDao;

    private LocalPostRepository(@NonNull PostDao dao,
                                @NonNull AppExecutors appExecutors) {

        mAppExecutors = appExecutors;
        mPostDao = dao;
    }

    public static LocalPostRepository getInstance(@NonNull PostDao dao, @NonNull AppExecutors appExecutors) {

        synchronized (LocalPostRepository.class) {
            if (INSTANCE == null) {
                INSTANCE = new LocalPostRepository(dao, appExecutors);
            }
        }

        return INSTANCE;
    }


    @Override
    public void getPosts(@NotNull PostDataRepository.LoadPostsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Post> posts = mPostDao.getAllPosts();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (posts.isEmpty()) {
                            callback.onPostFailedToLoad();
                        } else {
                            callback.onPostLoaded(posts);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPost(@NotNull String postId, @NotNull GetPostCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Post post = mPostDao.getPost(postId);
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (post != null) {
                            callback.onPostLoaded(post);
                        } else {
                            callback.onPostFailedToLoad();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void savePost(@NotNull Post post) {
        checkNotNull(post);

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mPostDao.insert(post);
            }
        };

        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void deletePost(@NotNull String postId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPostDao.deletePostById(postId);
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllPost() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mPostDao.deletePosts();
                ;
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    public void clearInstance() {
        INSTANCE = null;
    }
}
