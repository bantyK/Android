package com.example.admin.indianrail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    ApiInterface apiInterface;
    List<TrainRoute> trainStations;
    ListView routeList;
    Button searchButton;
    EditText trainNumberEditText;
    LinearLayout formLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUiElements();
        getTrainRouteListFromAPI();
    }

    private void initUiElements() {
        routeList = (ListView) findViewById(R.id.route_list_view);
        searchButton = (Button) findViewById(R.id.btn_search);
        trainNumberEditText = (EditText) findViewById(R.id.et_train_number);
        formLayout = (LinearLayout) findViewById(R.id.ll_form);
        showUIElements();
    }

    private void showUIElements() {
        formLayout.setVisibility(View.VISIBLE);
        routeList.setVisibility(View.INVISIBLE);
    }

    private void getTrainRouteListFromAPI() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TrainRouteResponse> call = apiInterface.getTrainRoute("22846", "wia6pcs2");
        call.enqueue(new Callback<TrainRouteResponse>() {
            @Override
            public void onResponse(Call<TrainRouteResponse> call, Response<TrainRouteResponse> response) {
                trainStations = response.body().getTrainRoutes();
                displayStaionsInList(trainStations);
            }

            @Override
            public void onFailure(Call<TrainRouteResponse> call, Throwable t) {

            }
        });
    }

    private void displayStaionsInList(List<TrainRoute> trainStations) {
        ArrayAdapter trainRouteListAdapter = new ListAdapter(trainStations, MainActivity.this);
        routeList.setAdapter(trainRouteListAdapter);
    }

    public void searchButtonClicked(View view) {
        if (view.getId() == R.id.btn_search) {
            hideUIElements();
            getTrainRouteListFromAPI();
        }
    }

    private void hideUIElements() {
        formLayout.setVisibility(View.INVISIBLE);
        routeList.setVisibility(View.VISIBLE);
    }
}
