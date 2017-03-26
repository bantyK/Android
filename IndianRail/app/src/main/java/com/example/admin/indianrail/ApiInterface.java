package com.example.admin.indianrail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("route/train/{train_no}/apikey/{apikey}")
    Call<TrainRouteResponse> getTrainRoute(@Path("train_no") String trainNumber, @Path("apikey") String apiKey);
}
