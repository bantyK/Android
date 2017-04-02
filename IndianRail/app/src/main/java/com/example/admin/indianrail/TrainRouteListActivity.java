package com.example.admin.indianrail;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrainRouteListActivity extends AppCompatActivity {

    private static final String TAG = TrainRouteListActivity.class.getSimpleName();
    ApiInterface apiInterface;
    ArrayList<TrainRoute> trainStations;
    Button searchButton;
    EditText trainNumberEditText;
    LinearLayout formLayout;
    FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_route_list);
        initUiElements();
    }

    private void initUiElements() {
        searchButton = (Button) findViewById(R.id.btn_search);
        trainNumberEditText = (EditText) findViewById(R.id.et_train_number);
        formLayout = (LinearLayout) findViewById(R.id.ll_form);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        showUIElements();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUIElements();
    }

    public void showUIElements() {
        formLayout.setVisibility(View.VISIBLE);
        fragmentContainer.setVisibility(View.INVISIBLE);
    }

    public void hideUIElements() {
        formLayout.setVisibility(View.INVISIBLE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    private void getTrainRouteListFromAPI() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String trainNumber = trainNumberEditText.getText().toString();
        Call<TrainRouteResponse> call = apiInterface.getTrainRoute(trainNumber, "wia6pcs2");
        call.enqueue(new Callback<TrainRouteResponse>() {
            @Override
            public void onResponse(Call<TrainRouteResponse> call, Response<TrainRouteResponse> response) {
                trainStations = response.body().getTrainRoutes();
                displayListInListFragment(trainStations);
                hideUIElements();

            }

            @Override
            public void onFailure(Call<TrainRouteResponse> call, Throwable t) {

            }
        });
    }

    private void displayListInListFragment(ArrayList<TrainRoute> trainStations) {
        ListFragment stationRouteListFragment = TrainStationListFragment.newInstance(trainStations, this);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragment_container, stationRouteListFragment, "tag");
        fragmentTransaction.addToBackStack("tag");
        fragmentTransaction.commit();
    }

    public void searchButtonClicked(View view) {
        if (view.getId() == R.id.btn_search) {
            getTrainRouteListFromAPI();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
