package com.example.vuclip.rxjava_kotlin.dagger

import com.example.vuclip.rxjava_kotlin.retrofit.WikiAppService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Banty on 31/03/18.
 */
@Module
class NetworkModule {

    @Provides
    fun provideWikiAppService(retrofit: Retrofit): WikiAppService {
        return retrofit.create(WikiAppService::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://en.wikipedia.org/w/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit
    }
}