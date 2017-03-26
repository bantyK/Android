package com.example.admin.indianrail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    List<TrainRoute> trainRouteList;
    Context context;
    private TextView serialNumber;
    private TextView stationName;
    private TextView stationCode;
    private TextView arrivalTime;
    private TextView departureTime;
    private TextView distance;
    private TextView day;
    private RelativeLayout listRowRoot;

    public ListAdapter(List<TrainRoute> trainRouteList, Context context) {
        super(context, R.layout.list_row, trainRouteList);
        this.trainRouteList = trainRouteList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trainRouteList.size();
    }

    @Override
    public Object getItem(int position) {
        return trainRouteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listRowView = inflater.inflate(R.layout.list_row, parent, false);
        initUIElements(listRowView);
        populateListRow(position, trainRouteList.get(position));
        return listRowView;

    }

    private void populateListRow(int position, TrainRoute trainRoute) {
        setOddRowBackgroundBlue(position);
        populateRowsWithTrainRouteData(position, trainRoute);
    }

    private void populateRowsWithTrainRouteData(int position, TrainRoute trainRoute) {
        serialNumber.setText(position + 1 + "");
        stationName.setText(trainRoute.getStationName());
        stationCode.setText(trainRoute.getStationCode());
        arrivalTime.setText(trainRoute.getScheduleArrival());
        departureTime.setText(trainRoute.getScheduleDeparture());
        distance.setText(trainRoute.getDistance());
        day.setText(trainRoute.getDay());
    }

    private void setOddRowBackgroundBlue(int position) {
        if (position % 2 != 0) {
            listRowRoot.setBackground(context.getResources().getDrawable(R.drawable.blue_background));
        }
    }

    private void initUIElements(View listRowView) {
        listRowRoot = (RelativeLayout) listRowView.findViewById(R.id.rl_list_row_root_view);
        serialNumber = (TextView) listRowView.findViewById(R.id.tv_serial_number);
        stationName = (TextView) listRowView.findViewById(R.id.tv_station_name);
        stationCode = (TextView) listRowView.findViewById(R.id.tv_station_code);
        arrivalTime = (TextView) listRowView.findViewById(R.id.tv_arrival_time);
        departureTime = (TextView) listRowView.findViewById(R.id.tv_departure_time);
        distance = (TextView) listRowView.findViewById(R.id.tv_distance);
        day = (TextView) listRowView.findViewById(R.id.tv_day);
    }
}
