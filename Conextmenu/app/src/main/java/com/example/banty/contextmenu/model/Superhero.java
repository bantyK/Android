package com.example.banty.contextmenu.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by banty on 19/8/17.
 */

public class Superhero {
    @SerializedName("name")
    private String title;
    @SerializedName("image_url")
    private String imagePath;
    @SerializedName("franchise")
    private String productionCompany;
    @SerializedName("real_name")
    private String realName;
    @SerializedName("played_by")
    private String playedBy;

    public Superhero(String title, String imagePath, String productionCompany, String realName, String playedBy) {
        this.title = title;
        this.imagePath = imagePath;
        this.productionCompany = productionCompany;
        this.realName = realName;
        this.playedBy = playedBy;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(String playedBy) {
        this.playedBy = playedBy;
    }

    public String getTitle() {
        return title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }
}

