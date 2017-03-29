package com.example.admin.indianrail;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrainRouteResponse {
    @SerializedName("name")
    private String trainName;
    @SerializedName("number")
    private String trainNumber;
    @SerializedName("route")
    private ArrayList<TrainRoute> trainRoutes;

    public TrainRouteResponse(String trainName, String trainNumber, ArrayList<TrainRoute> trainRoutes) {
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

    public ArrayList<TrainRoute> getTrainRoutes() {
        return trainRoutes;
    }
}
