package com.example.admin.indianrail;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class TrainStationListFragment extends ListFragment {
    public static TrainStationListFragment newInstance(ArrayList<TrainRoute> trainStations) {
        Bundle args = new Bundle();
        args.putParcelableArrayList("train_stations", trainStations);
        TrainStationListFragment fragment = new TrainStationListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<TrainRoute> trainStations = getArguments().getParcelableArrayList("train_stations");
        ListAdapter listAdapter = new ListAdapter(trainStations,getActivity());
        getListView().setAdapter(listAdapter);
    }
}
