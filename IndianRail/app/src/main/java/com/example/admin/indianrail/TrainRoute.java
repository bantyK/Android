package com.example.admin.indianrail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TrainRoute implements Parcelable {

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

    protected TrainRoute(Parcel in) {
        distance = in.readString();
        scheduleArrival = in.readString();
        state = in.readString();
        day = in.readString();
        stationName = in.readString();
        scheduleDeparture = in.readString();
        stationCode = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(distance);
        dest.writeString(scheduleArrival);
        dest.writeString(state);
        dest.writeString(day);
        dest.writeString(stationName);
        dest.writeString(scheduleDeparture);
        dest.writeString(stationCode);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<TrainRoute> CREATOR = new Parcelable.Creator<TrainRoute>() {
        @Override
        public TrainRoute createFromParcel(Parcel in) {
            return new TrainRoute(in);
        }

        @Override
        public TrainRoute[] newArray(int size) {
            return new TrainRoute[size];
        }
    };
}
