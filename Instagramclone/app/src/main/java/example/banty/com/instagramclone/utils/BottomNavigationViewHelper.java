package example.banty.com.instagramclone.utils;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewHelper {

    private static final String TAG = BottomNavigationViewHelper.class.getSimpleName();

    public static void setUpBottomNavigationView (BottomNavigationViewEx navigationView){
        navigationView.enableAnimation(false);
        navigationView.enableItemShiftingMode(false);
        navigationView.enableShiftingMode(false);
        navigationView.setTextVisibility(false);
    }
}
