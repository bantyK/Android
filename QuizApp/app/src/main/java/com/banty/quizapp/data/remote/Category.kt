package com.banty.quizapp.data.remote

import com.squareup.moshi.Json

/**
 * Created by Banty on 18/06/18.
 */
data class Category(

        @Json(name = "id")
        val id: Int,

        @Json(name = "name")
        val name: String
)