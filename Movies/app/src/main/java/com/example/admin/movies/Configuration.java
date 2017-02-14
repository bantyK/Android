package com.example.admin.movies;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Configuration {
    private String baseURL;
    private String secureBaseURL;
    private ArrayList<String> backdropSizes;

    public Configuration(String baseURL, String secureBaseURL, ArrayList<String> backdropSizes) {
        this.baseURL = baseURL;
        this.secureBaseURL = secureBaseURL;
        this.backdropSizes = backdropSizes;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getSecureBaseURL() {
        return secureBaseURL;
    }

    public ArrayList<String> getBackdropSizes() {
        return backdropSizes;
    }
}
