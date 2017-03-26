package com.example.admin.indianrail;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainRouteResponse {
    @SerializedName("name")
    private String trainName;
    @SerializedName("number")
    private String trainNumber;
    @SerializedName("route")
    private List<TrainRoute> trainRoutes;

    public TrainRouteResponse(String trainName, String trainNumber, List<TrainRoute> trainRoutes) {
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.trainRoutes = trainRoutes;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public List<TrainRoute> getTrainRoutes() {
        return trainRoutes;
    }
}
