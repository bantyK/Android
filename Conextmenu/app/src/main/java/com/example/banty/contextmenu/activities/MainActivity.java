package com.example.banty.contextmenu.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.banty.contextmenu.R;
import com.example.banty.contextmenu.adapter.SectionPagerAdapter;
import com.example.banty.contextmenu.fragment.RecyclerViewFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViewPager();
    }

    private void setUpViewPager() {
        SectionPagerAdapter pagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new RecyclerViewFragment());
        pagerAdapter.addFragment(new RecyclerViewFragment());

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Tab 1");
        tabLayout.getTabAt(1).setText("Tab 2");
    }

}
