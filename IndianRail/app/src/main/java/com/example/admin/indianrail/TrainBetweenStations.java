package com.example.admin.indianrail;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TrainBetweenStations extends AppCompatActivity {

    public static final String TAG = TrainBetweenStations.class.getSimpleName();
    Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    //UI Elements
    private EditText dayOfJourneyEditText;
    private EditText sourceStationEditText;
    private EditText destinationStationEditText;

    String dayOfJourney,sourceStation,destinationStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_between_stations);
        initUIElements();

    }

    private void initUIElements() {
        dayOfJourneyEditText = (EditText) findViewById(R.id.et_date);
        sourceStationEditText = (EditText) findViewById(R.id.et_source_station);
        destinationStationEditText = (EditText) findViewById(R.id.et_destination_station);
    }

    public void dateEditTextClicked(View view) {
        calendar = Calendar.getInstance();
        setDateSetListener();
        new DatePickerDialog(TrainBetweenStations.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void submitButtonClicked(View view) {


    }

    private void setDateSetListener() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateEditText();
            }
        };
    }

    private void updateEditText() {
        String dateFormat = "dd-MM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        dayOfJourneyEditText.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
