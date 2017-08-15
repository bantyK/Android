package example.banty.com.instagramclone.activities.share;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;
import example.banty.com.instagramclone.adapters.SectionsPageAdapter;
import example.banty.com.instagramclone.utils.Permissions;

public class ShareActivity extends BaseActivity {

    private static final String TAG = "ShareActivity";
    private static final int MENU_POSITION = 2;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_share);


        if(checkPermissionsArray(Permissions.PERMISSIONS)) {
            // all the permissions are granted
            setUpViewPagerAdapter();
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
    public boolean checkPermissionsArray(String[] permissions) {
        Log.d(TAG, "checkPermissionsArray: checking permissions array");

        for(String permission : permissions) {
            if(!checkSinglePermission(permission)) {
                return false;
            }
        }
        return true;
    }

    //returns true if the @param permission is granted
    public boolean checkSinglePermission(String permission) {
        Log.d(TAG, "checkSinglePermission: checking permission : " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(this, permission);

        return permissionRequest == PackageManager.PERMISSION_GRANTED;
    }

    public void setUpViewPagerAdapter() {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new GallaryFragment());
        adapter.addFragment(new PhotoFragment());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_bottom);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText(getString(R.string.gallary));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));
    }

    //return the current tab number
    //0 = Gallary Fragment
    //1 = Photo Fragment
    public int getCurrentItem() {
        return viewPager.getCurrentItem();
    }
}
