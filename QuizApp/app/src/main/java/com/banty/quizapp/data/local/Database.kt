package com.banty.quizapp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.banty.quizapp.data.Question

/**
 * Created by Banty on 17/06/18.
 */
@Database(entities = arrayOf(Question::class), version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun getQuestionDao(): QuestionDao
}