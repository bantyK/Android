package example.banty.com.instagramclone.activities.profile;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.adapters.SectionsStatePagerAdapter;

public class AccountSettingActivity extends BaseActivity {
    private static final String TAG = "AccountSettingActivity";
    private View.OnClickListener backArrowClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    private ViewPager viewPager;
    private RelativeLayout relativeLayout;
    private SectionsStatePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ImageView backArrow = (ImageView) findViewById(R.id.iv_back_arrow);
        backArrow.setOnClickListener(backArrowClicked);


        viewPager = (ViewPager) findViewById(R.id.container);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout_1);

        setUpSettingList();
        setUpFragments();
    }

    private void setUpSettingList() {
        Log.d(TAG, "setUpSettingList: initializing the account setting list");
        ListView accountOptionListview = (ListView) findViewById(R.id.lv_settings);

        ArrayList<String> settingOptions = new ArrayList<>();
        settingOptions.add(getString(R.string.edit_profile));
        settingOptions.add(getString(R.string.sign_out));

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,settingOptions);
        accountOptionListview.setAdapter(adapter);

        accountOptionListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating to fragment#" + position);
                setViewPager(position);
            }
        });
    }
    private void setViewPager(int fragmentNumber) {
        relativeLayout.setVisibility(View.GONE);
        Log.d(TAG, "setViewPager: navigating to fragment number = " + fragmentNumber);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(fragmentNumber);
    }


    private void setUpFragments () {
        pagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new EditProfileFragment(),getString(R.string.edit_profile));
        pagerAdapter.addFragment(new SignOutFragment(),getString(R.string.sign_out));
    }
}
