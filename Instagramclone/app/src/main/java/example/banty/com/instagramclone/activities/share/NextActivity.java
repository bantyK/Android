package example.banty.com.instagramclone.activities.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 20/08/17.
 */

public class NextActivity extends AppCompatActivity {

    private static final String TAG = "NextActivity";

    private String imageURLPassed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_next_activity);

        imageURLPassed = getImageFromIntent();
        Log.d(TAG, "onCreate: imageURL passed : " + imageURLPassed);
    }

    public String getImageFromIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(getString(R.string.selected_image))) {
            imageURLPassed = intent.getStringExtra(getString(R.string.selected_image));
        } else {
            imageURLPassed = "";
        }
        return imageURLPassed;
    }
}
