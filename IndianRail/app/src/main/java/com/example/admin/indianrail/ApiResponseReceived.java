package com.example.admin.indianrail;

import java.util.List;

public interface ApiResponseReceived {
    void responseReceived(List<TrainRoute> routes);
}
