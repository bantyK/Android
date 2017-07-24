package example.banty.com.instagramclone.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.activities.login.LoginActivity;
import example.banty.com.instagramclone.adapters.SectionsPageAdapter;

public class HomeActivity extends BaseActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final int MENU_POSITION = 0;
    private static final int CAMERA_POSITION_VIEW_PAGER = 0;
    private static final int HOME_POSITION_VIEW_PAGER = 1;
    private static final int MESSAGES_POSITION_VIEW_PAGER = 2;

    //Firebase Auth
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bottomNavigationView = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationView, MENU_POSITION);

        setUpViewPager();

        setFirebaseAuth();
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

    private void setFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: user signed in");

                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: user signed out");
                    launchLoginActivity();
                }
            }
        };
    }



    private void launchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
        checkUserLoginStatus(mFirebaseAuth.getCurrentUser());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkUserLoginStatus(FirebaseUser currentUser) {
        if (currentUser == null) {
            launchLoginActivity();
        }
    }

}
