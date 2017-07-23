package example.banty.com.instagramclone.activities.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.adapters.GridImageAdapter;
import example.banty.com.instagramclone.utils.UniversalImageLoader;

public class ProfileActivity extends BaseActivity {

    private static final String TAG = "ProfileActivity";
    private static final int MENU_POSITION = 4;

    private ProgressBar progressBar;
    private ImageView profilePhoto;
    private final int NUM_IMAGES_PER_COLUMN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_profile);
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationViewEx, MENU_POSITION);
        initUI();
        initImageLoader();
        setUpProfilePhoto();
        setToolBar();
        tempGridSetup();
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);

        ImageView profileMenu = (ImageView) findViewById(R.id.iv_profile_menu);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigate to account settings");
                Intent settingsIntent = new Intent(ProfileActivity.this, AccountSettingActivity.class);
                startActivity(settingsIntent);
            }
        });
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
    }

    private void setUpProfilePhoto() {
        String imageURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2015/01/podcast-event.jpg?itok=zsMimlTM";
        UniversalImageLoader.setImage(imageURL, profilePhoto, null, "https://");
    }

    private void initUI() {
        progressBar = (ProgressBar) findViewById(R.id.profile_progress_bar);
        progressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) findViewById(R.id.profile_image);
    }

    private void setImageGridView(ArrayList<String> imageURLList) {
        GridView gridView = (GridView) findViewById(R.id.image_gridview);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth / NUM_IMAGES_PER_COLUMN;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(this,R.layout.layout_grid_imageview,"",imageURLList);
        gridView.setAdapter(adapter);
    }

    private void tempGridSetup() {
        ArrayList<String> poodsImages = new ArrayList<>();
        poodsImages.add("http://ichef.bbci.co.uk/news/976/cpsprodpb/17156/production/_87705549_gettyimages-494848194.jpg");
        poodsImages.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQtB11ZVA_KWuoHtoIy6f9d1gAmb-sqW0XoMq3k1jkeSDEyFS-s");
        poodsImages.add("https://dvsgaming.org/wp-content/uploads/2017/02/pewdiepie-revelmode.jpg");
        poodsImages.add("https://peopledotcom.files.wordpress.com/2016/08/pewdiepie-800.jpg?w=800");
        poodsImages.add("https://static.independent.co.uk/s3fs-public/thumbnails/image/2014/08/01/17/PewDiePie.jpg");

        setImageGridView(poodsImages);

    }
}
