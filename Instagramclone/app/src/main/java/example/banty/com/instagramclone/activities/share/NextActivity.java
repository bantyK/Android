package example.banty.com.instagramclone.activities.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.utils.UniversalImageLoader;

/**
 * Created by banty on 20/08/17.
 */

public class NextActivity extends AppCompatActivity {

    private static final String TAG = "NextActivity";

    private String imageURLPassed;

    //Firebase Variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    //UI Elements
    private ImageView backArrowImage;
    private TextView shareText;
    private ImageView shareImage;

    private final String fileAppend = "file:/";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_next_activity);

        imageURLPassed = getImageFromIntent();
        initUIElems();
        setImageOnActivityCreate(imageURLPassed);
        setFirebaseAuth();
    }

    public void initUIElems() {
        backArrowImage = (ImageView) findViewById(R.id.iv_back_arrow);
        shareText = (TextView) findViewById(R.id.tv_share);
        shareImage = (ImageView) findViewById(R.id.image_share);

        //back image click listener
        backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing the Next Activity");
                finish();
            }
        });

        //share text click listener
        shareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: upload image into firebase database");
            }
        });

    }


    /*Get the image URL passed from the GallaryFragment as Intent value*/
    public String getImageFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.selected_image))) {
            imageURLPassed = intent.getStringExtra(getString(R.string.selected_image));
        } else {
            imageURLPassed = "";
        }
        return imageURLPassed;
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
                } else {
                    //User is signed out
                    Log.d(TAG, "onAuthStateChanged: user signed out");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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

    public void setImageOnActivityCreate(String imageURLPassed) {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(this);
        ImageLoader.getInstance().init(universalImageLoader.getImageLoaderConfig());
        UniversalImageLoader.setImage(imageURLPassed, shareImage, null, fileAppend);
    }

    private void uploadImage() {
        /*
            1. Create a data model for photo
            2. Add properties to the photo object (caption, date, imageURL, photoid, tags, userid)
            3. Count the number of photos user already has
            4. Upload the photo to the Firebase storage and insert new nodes in the Firebase Database

         */

    }
}
