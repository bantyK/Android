package com.example.banty.contextmenu.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.banty.contextmenu.R;
import com.example.banty.contextmenu.fragment.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFragment();
    }

    private void setUpFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new RecyclerViewFragment())
                .commit();
    }


}
