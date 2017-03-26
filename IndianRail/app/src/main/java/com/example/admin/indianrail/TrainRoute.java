package com.example.admin.indianrail;

import com.google.gson.annotations.SerializedName;

public class TrainRoute {

    @SerializedName("distance")
    private String distance;
    @SerializedName("scharr")
    private String scheduleArrival;
    @SerializedName("state")
    private String state;
    @SerializedName("day")
    private String day;
    @SerializedName("fullname")
    private String stationName;
    @SerializedName("schdep")
    private String scheduleDeparture;
    @SerializedName("code")
    private String stationCode;

    public TrainRoute(String distance, String scheduleArrival, String state, String day, String stationName,
                      String scheduleDeparture, String stationCode) {
        this.distance = distance;
        this.scheduleArrival = scheduleArrival;
        this.state = state;
        this.day = day;
        this.stationName = stationName;
        this.scheduleDeparture = scheduleDeparture;
        this.stationCode = stationCode;
    }

    public String getDistance() {
        return distance;
    }

    public String getScheduleArrival() {
        return scheduleArrival;
    }

    public String getState() {
        return state;
    }

    public String getDay() {
        return day;
    }

    public String getStationName() {
        return stationName;
    }

    public String getScheduleDeparture() {
        return scheduleDeparture;
    }

    public String getStationCode() {
        return stationCode;
    }
}
