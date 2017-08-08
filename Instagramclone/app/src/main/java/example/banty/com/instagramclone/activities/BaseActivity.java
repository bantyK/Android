package example.banty.com.instagramclone.activities;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.listeners.NetworkConnectivityListener;
import example.banty.com.instagramclone.receivers.NetworkBroadcastReceiver;
import example.banty.com.instagramclone.utils.BottomNavigationViewHelper;

public class BaseActivity extends AppCompatActivity implements NetworkConnectivityListener{

    private static final String TAG = BaseActivity.class.getSimpleName();
    private NetworkBroadcastReceiver networkBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkBroadcastReceiver();
    }

    private void registerNetworkBroadcastReceiver() {
        IntentFilter connectionFilter = new IntentFilter();
        connectionFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        networkBroadcastReceiver = new NetworkBroadcastReceiver(this);

        registerReceiver(networkBroadcastReceiver, connectionFilter);
    }

    /*Bottom navigation bar setup*/
    public void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx, int position) {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewHelper.setUpBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);

        //highlight the current menu item in the navigation bar
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(position);
        menuItem.setChecked(true);

    }

    @Override
    public void onNetworkConnected() {
        Log.d(TAG, "onNetworkConnected : network connected");

    }

    @Override
    public void onNetworkDisconnected() {
        Log.d(TAG, "onNetworkDisconnected : network disconnected");
        Toast.makeText(this, R.string.no_network, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkBroadcastReceiver);
    }
}
