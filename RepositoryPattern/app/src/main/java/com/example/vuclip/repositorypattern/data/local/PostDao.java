package com.example.vuclip.repositorypattern.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vuclip.repositorypattern.model.Post;

import java.util.List;

/**
 * Created by Banty on 08/04/18.
 */
@Dao
public interface PostDao {
    @Query("SELECT * FROM posts")
    List<Post> getAllPosts();

    @Query("SELECT * FROM posts where id = :postId")
    Post getPost(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);

    @Update
    int updatePost(Post post);

    @Query("UPDATE posts SET likes = :count WHERE id = :postId")
    void updateLikes(int count, String postId);

    @Query("DELETE FROM posts where id = :postId")
    void deletePostById(String postId);

    @Query("DELETE FROM posts")
    void deletePosts();
}
