package com.example.admin.movies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkBroadcastReceiver.class.getSimpleName();

    public NetworkBroadcastReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: network change");
        if (isOnline(context)) {
            Log.d(TAG, "onReceive: connected");
        } else {
            Log.d(TAG, "onReceive: not connected");
        }
    }


    public boolean isOnline(Context mContext) {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
