package example.banty.com.instagramclone.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
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

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import de.hdodenhof.circleimageview.CircleImageView;
import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.utils.BottomNavigationViewHelper;

/**
 * Created by banty on 12/8/17.
 */

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";

    private static final int MENU_POSITION = 4;

    private TextView postTextView, followerTextView, followingTextView, displayNameTextView,
            usernameTextView, websiteTextView, descriptionTextView;
    private ProgressBar progressBar;
    private CircleImageView profilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationViewEx bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setUpUiElements(view);
        return view;
    }

    private void setUpUiElements(View view) {
        displayNameTextView = (TextView) view.findViewById(R.id.display_name);
        usernameTextView = (TextView) view.findViewById(R.id.username);
        websiteTextView = (TextView) view.findViewById(R.id.website);
        descriptionTextView = (TextView) view.findViewById(R.id.description);
        profilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
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
}
