package example.banty.com.instagramclone.activities.home;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.adapters.SectionsPageAdapter;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int MENU_POSITION = 0;
    private static final int CAMERA_POSITION_VIEW_PAGER = 0;
    private static final int HOME_POSITION_VIEW_PAGER = 1;
    private static final int MESSAGES_POSITION_VIEW_PAGER = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationView, MENU_POSITION);

        setUpViewPager();
    }

    /*
    * Setting up the pager adapter for Home Camera and Messages Fragment
    * */
    private void setUpViewPager() {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        sectionsPageAdapter.addFragment(new CameraFragment());
        sectionsPageAdapter.addFragment(new HomeFragment());
        sectionsPageAdapter.addFragment(new MessagesFragment());

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(CAMERA_POSITION_VIEW_PAGER).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(HOME_POSITION_VIEW_PAGER).setIcon(R.drawable.instagram_icon);
        tabLayout.getTabAt(MESSAGES_POSITION_VIEW_PAGER).setIcon(R.drawable.ic_messages);

    }


}
