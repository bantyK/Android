package example.banty.com.instagramclone.activities.share;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.utils.Permissions;

public class ShareActivity extends BaseActivity {

    private static final String TAG = "ShareActivity";
    private static final int MENU_POSITION = 2;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_share);

        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_nav_view_bar);
        setupBottomNavigationView(bottomNavigationViewEx, MENU_POSITION);

        if(checkPermissionsArray(Permissions.PERMISSIONS)) {
            // all the permissions are granted
        } else {
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    private void verifyPermissions(String[] permissions) {
        Log.d(TAG, "verifyPermissions: verifying permissions");

        ActivityCompat.requestPermissions(this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST);
    }

    //returns true if all the permissions are granted
    private boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissionsArray: checking permissions array");

        for(String permission : permissions) {
            if(!checkSinglePermission(permission)) {
                return false;
            }
        }
        return true;
    }

    //returns true if the @param permission is granted
    private boolean checkSinglePermission(String permission) {
        Log.d(TAG, "checkSinglePermission: checking permission : " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(this, permission);

        return permissionRequest == PackageManager.PERMISSION_GRANTED;
    }
}
