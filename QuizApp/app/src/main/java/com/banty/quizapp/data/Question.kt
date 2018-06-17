package com.banty.quizapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Banty on 17/06/18.
 *
 * Represents a question with options and right answer
 *
 */
@Entity(tableName = "questions")
data class Question(

        @PrimaryKey
        val id : String, // id will be combination of (category + type + difficulty)

        @Json(name = "category")
        val category: String? = null,

        @Json(name = "type")
        val type: String? = null,

        @Json(name = "question")
        val question: String? = null,

        @Json(name = "difficulty")
        val difficulty: String? = null,

        @Json(name = "correct_answer")
        val correctAnswer: String? = null,

        @Json(name = "incorrect_answer")
        val incorrectAnswers: ArrayList<String?>
)