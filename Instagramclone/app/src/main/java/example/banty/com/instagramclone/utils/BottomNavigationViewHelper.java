package example.banty.com.instagramclone.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.home.HomeActivity;
import example.banty.com.instagramclone.activities.likes.LikesActivity;
import example.banty.com.instagramclone.activities.profile.ProfileActivity;
import example.banty.com.instagramclone.activities.search.SearchActivity;
import example.banty.com.instagramclone.activities.share.ShareActivity;

public class BottomNavigationViewHelper {

    private static final String TAG = BottomNavigationViewHelper.class.getSimpleName();

    public static void setUpBottomNavigationView(BottomNavigationViewEx navigationView) {
        navigationView.enableAnimation(false);
        navigationView.enableItemShiftingMode(false);
        navigationView.enableShiftingMode(false);
        navigationView.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, final BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.ic_house:
                        Log.d(TAG, "onNavigationItemSelected: home selected");
                        Intent homeActivityIntent = new Intent(context, HomeActivity.class);
                        context.startActivity(homeActivityIntent);
                        break;
                    case R.id.ic_search:
                        Log.d(TAG, "onNavigationItemSelected: search selected");
                        Intent seachActivityIntent = new Intent(context, SearchActivity.class);
                        context.startActivity(seachActivityIntent);
                        break;
                    case R.id.ic_circle:
                        Log.d(TAG, "onNavigationItemSelected: share selected");
                        Intent shareActivityIntent = new Intent(context, ShareActivity.class);
                        context.startActivity(shareActivityIntent);
                        break;
                    case R.id.ic_alert:
                        Log.d(TAG, "onNavigationItemSelected: alert selected");
                        Intent likesActivityIntent = new Intent(context, LikesActivity.class);
                        context.startActivity(likesActivityIntent);
                        break;
                    case R.id.ic_android:
                        Log.d(TAG, "onNavigationItemSelected: profile selected");
                        Intent profileActivityIntent = new Intent(context, ProfileActivity.class);
                        context.startActivity(profileActivityIntent);
                        break;
                }

                return false;
            }
        });
    }
}
