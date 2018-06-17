package com.example.vuclip.repositorypattern.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vuclip.repositorypattern.model.Post;

/**
 * Created by Banty on 08/04/18.
 */
@Database(entities = {Post.class}, version = 1)
public abstract class PostDatabase extends RoomDatabase {

    private static PostDatabase INSTANCE = null;

    public abstract PostDao postDao();

    private static final Object sLock = new Object();

    public static PostDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PostDatabase.class, "tasks.db")
                        .build();
            }
            return INSTANCE;
        }
    }
}
