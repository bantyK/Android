package example.banty.com.instagramclone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static boolean isConnectedToInternet(Context context) {
        boolean isConnectedToInternet = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] allNetworkInfo = cm.getAllNetworkInfo();
        for (NetworkInfo networkInfo : allNetworkInfo) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
                if (networkInfo.isConnected())
                    isConnectedToInternet = true;
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
                if (networkInfo.isConnected())
                    isConnectedToInternet = true;
        }
        return isConnectedToInternet;
    }
}
