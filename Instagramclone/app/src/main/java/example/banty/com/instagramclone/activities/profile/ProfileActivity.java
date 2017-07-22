package example.banty.com.instagramclone.activities.profile;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = "ProfileActivity";
    private static final int MENU_POSITION = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_profile);
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationViewEx, MENU_POSITION);

        setUpToolbar();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: clicked menu item");
                switch (item.getItemId()) {
                    case R.id.profile_menu:
                        Log.d(TAG, "onMenuItemClick: profile menu navigated");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;

    }
}
