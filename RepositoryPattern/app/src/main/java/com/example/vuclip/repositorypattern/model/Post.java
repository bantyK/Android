package com.example.vuclip.repositorypattern.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Banty on 08/04/18.
 */
@Entity(tableName = "posts")
public class Post {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;

    @Nullable
    @ColumnInfo(name = "title")
    private final String title;

    @Nullable
    @ColumnInfo(name = "likes")
    private int likes;


    /**
     * Initialise a new post if an id is available.
     */
    public Post(@NonNull String id, @Nullable String title) {
        this.id = id;
        this.title = title;
        likes = 0;
    }

    /**
     * Initialise a new post if id is not available
     */
    @Ignore
    public Post(@NonNull String title) {
        this(UUID.randomUUID().toString(), title);
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return String.format("Post with title : %s", title);
    }

    public void setLikes(@Nullable int likes) {
        this.likes = likes;
    }
}
