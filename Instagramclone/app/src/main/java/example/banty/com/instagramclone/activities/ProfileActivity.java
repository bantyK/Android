package example.banty.com.instagramclone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.banty.com.instagramclone.R;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
}
