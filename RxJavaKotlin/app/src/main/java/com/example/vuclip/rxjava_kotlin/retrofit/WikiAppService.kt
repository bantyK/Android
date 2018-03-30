package com.example.vuclip.rxjava_kotlin.retrofit

import com.example.vuclip.rxjava_kotlin.models.Models
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Banty on 31/03/18.
 */
interface WikiAppService {

    @GET("api.php")
    fun hitCountCheck(@Query("action") action: String,
                      @Query("format") format: String,
                      @Query("list") list: String,
                      @Query("srsearch") srsearch: String):
            Observable<Models.Result>
}