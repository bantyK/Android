package com.example.banty.contextmenu.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by banty on 19/8/17.
 */

public class ApiClient {
    private static final String BASE_URL = "https://api.myjson.com/bins/14j421/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;

    }
}
