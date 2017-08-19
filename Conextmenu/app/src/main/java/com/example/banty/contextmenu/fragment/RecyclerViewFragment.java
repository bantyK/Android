package com.example.banty.contextmenu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.banty.contextmenu.R;
import com.example.banty.contextmenu.adapter.RecyclerAdapter;
import com.example.banty.contextmenu.model.Superhero;
import com.example.banty.contextmenu.retrofit.ApiClient;
import com.example.banty.contextmenu.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by banty on 19/8/17.
 */

public class RecyclerViewFragment extends Fragment {
    private static final String TAG = "RecyclerViewFragment";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: recycler view fragment created");
        View view = inflater.inflate(R.layout.fragment, container, false);
        initUIElements(view);
        getJsonFromApi();
        return view;
    }

    private void initUIElements(View view) {
        recyclerView = view.findViewById(R.id.recyler_view);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    private void setUpRecylerView(List<Superhero> superheroList) {
        RecyclerAdapter adapter = new RecyclerAdapter(superheroList,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getJsonFromApi() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<Superhero>> responseCall = apiService.getMovies();

        responseCall.enqueue(new Callback<List<Superhero>>() {
            @Override
            public void onResponse(Call<List<Superhero>> call, Response<List<Superhero>> response) {
                Log.d(TAG, "onResponse: response from api : " + response.body());
                List<Superhero> superheroList = response.body();
                progressBar.setVisibility(View.GONE);
                setUpRecylerView(superheroList);
            }

            @Override
            public void onFailure(Call<List<Superhero>> call, Throwable t) {
                Log.d(TAG, "onFailure: failed to get the movie list : " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected: item selected : " + item.toString());
        switch(item.toString()){
            case "New":
                Log.d(TAG, "onContextItemSelected: new menu item is selected");
                break;
            case "Open":
                Log.d(TAG, "onContextItemSelected: open menu item is selected");
                break;
            case "Delete":
            Log.d(TAG, "onContextItemSelected: save menu item is selected");
            break;
        }
        return super.onContextItemSelected(item);
    }
}

