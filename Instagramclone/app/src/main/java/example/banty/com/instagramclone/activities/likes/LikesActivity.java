package example.banty.com.instagramclone.activities.likes;

import android.os.Bundle;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;

public class LikesActivity extends BaseActivity {

    private static final String TAG = "LikesActivity";
    private static final int MENU_POSITION = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_likes);

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationView, MENU_POSITION);
    }

}
