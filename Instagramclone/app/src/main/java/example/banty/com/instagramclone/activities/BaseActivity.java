package example.banty.com.instagramclone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.utils.BottomNavigationViewHelper;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*Bottom navigation bar setup*/
    public void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx, int position) {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);

        //highlight the current menu item in the navigation bar
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(position);
        menuItem.setChecked(true);

    }
}
