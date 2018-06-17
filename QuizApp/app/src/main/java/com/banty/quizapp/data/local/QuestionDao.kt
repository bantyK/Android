package com.banty.quizapp.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.banty.quizapp.data.Question
import io.reactivex.Single

/**
 * Created by Banty on 17/06/18.
 */

@Dao
interface QuestionDao {

    @Query("SELECT * from questions where id = :id")
    fun getQuestions(id: String): Single<List<Question>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question)

}