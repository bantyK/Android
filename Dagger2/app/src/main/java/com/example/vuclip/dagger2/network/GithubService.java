package com.example.vuclip.dagger2.network;

import com.example.vuclip.dagger2.models.GithubRepo;
import com.example.vuclip.dagger2.models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Banty on 10/12/17.
 */

public interface GithubService {

    @GET("users/{username}/repos")
    Call<List<GithubRepo>> getReposForUser(@Path("username") String username);

    @GET("repositories")
    Call<List<GithubRepo>> getAllRepos();

    @GET("users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);
}