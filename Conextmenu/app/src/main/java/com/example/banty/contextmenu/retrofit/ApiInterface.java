package com.example.banty.contextmenu.retrofit;

import com.example.banty.contextmenu.model.Superhero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by banty on 19/8/17.
 */

public interface ApiInterface {
    @GET("./")
    Call<List<Superhero>> getMovies();
}
