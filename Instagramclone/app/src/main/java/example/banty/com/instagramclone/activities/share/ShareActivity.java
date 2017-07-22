package example.banty.com.instagramclone.activities.share;

import android.os.Bundle;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;

public class ShareActivity extends BaseActivity {

    private static final String TAG = "ShareActivity";
    private static final int MENU_POSITION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_share);

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationViewEx, MENU_POSITION);
    }
}
