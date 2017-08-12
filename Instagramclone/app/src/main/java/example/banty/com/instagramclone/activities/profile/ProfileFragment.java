package example.banty.com.instagramclone.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;
import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.models.UserAccountSettings;
import example.banty.com.instagramclone.models.UserSetting;
import example.banty.com.instagramclone.utils.BottomNavigationViewHelper;
import example.banty.com.instagramclone.utils.FirebaseHelper;
import example.banty.com.instagramclone.utils.UniversalImageLoader;

/**
 * Created by banty on 12/8/17.
 */

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private static final int MENU_POSITION = 4;

    private TextView postTextView, followerTextView, followingTextView, displayNameTextView,
            usernameTextView, profileInfoTextView, descriptionTextView;
    private ProgressBar progressBar;
    private CircleImageView profilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationViewEx bottomNavigationView;

    //Firebase Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpUiElements(view);
        initImageLoader();
        setFirebaseAuth();

        return view;
    }

    private void setUpUiElements(View view) {
        displayNameTextView = (TextView) view.findViewById(R.id.display_name);
        profileInfoTextView = (TextView) view.findViewById(R.id.profile_info);
        descriptionTextView = (TextView) view.findViewById(R.id.profile_description);
        profilePhoto = (CircleImageView) view.findViewById(R.id.profile_image);
        postTextView = (TextView) view.findViewById(R.id.tv_posts);
        followerTextView = (TextView) view.findViewById(R.id.tv_followers);
        followingTextView = (TextView) view.findViewById(R.id.tv_following);

        progressBar = (ProgressBar) view.findViewById(R.id.profile_progress_bar);
        gridView = (GridView) view.findViewById(R.id.grid_image_view);
        toolbar = (Toolbar) view.findViewById(R.id.profile_toolbar);
        profileMenu = (ImageView) view.findViewById(R.id.iv_profile_menu);
        bottomNavigationView = (BottomNavigationViewEx) view.findViewById(R.id.bottom_nav_view_bar);

        setupBottomNavigationView(bottomNavigationView, MENU_POSITION);
        setToolBar();
    }

    /*Bottom navigation bar setup*/
    public void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx, int position) {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(getActivity() , bottomNavigationViewEx);

        //highlight the current menu item in the navigation bar
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(position);
        menuItem.setChecked(true);

    }

    private void setToolBar() {
        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigate to account settings");
                Intent settingsIntent = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(settingsIntent);
            }
        });
    }

    private void setFirebaseAuth() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "onAuthStateChanged: user signed in");
                    userId = user.getUid();

                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: user signed out");

                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user information from database
                UserSetting userSetting = new FirebaseHelper(getContext()).getUserAccountSetting(dataSnapshot,userId);
                setWidgetValuesFromDB(userSetting);
                //retrieve images for user
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setWidgetValuesFromDB(UserSetting userSetting) {

        UserAccountSettings userAccountSettings = userSetting.getSettings();

        UniversalImageLoader.setImage(userAccountSettings.getProfile_photo(), profilePhoto, null, "");
        displayNameTextView.setText(userAccountSettings.getDisplay_name());
        profileInfoTextView.setText(userAccountSettings.getWebsite());
        descriptionTextView.setText(userAccountSettings.getDescription());
        postTextView.setText(String.valueOf(userAccountSettings.getPosts()));
        followingTextView.setText(String.valueOf(userAccountSettings.getFollowing()));
        followerTextView.setText(String.valueOf(userAccountSettings.getFollowers()));

    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }
}
