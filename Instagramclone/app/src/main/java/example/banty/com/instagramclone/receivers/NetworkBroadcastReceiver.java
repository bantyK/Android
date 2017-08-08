package example.banty.com.instagramclone.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import example.banty.com.instagramclone.listeners.NetworkConnectivityListener;
import example.banty.com.instagramclone.utils.NetworkUtil;


public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private NetworkConnectivityListener listener;

    public NetworkBroadcastReceiver() {
    }

    public NetworkBroadcastReceiver(NetworkConnectivityListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (listener != null) {
            if (NetworkUtil.isConnectedToInternet(context)) {
                System.out.println("NBR : Receiver connected");
                listener.onNetworkConnected();
            } else {
                System.out.println("NBR : Receiver disconnected");
                listener.onNetworkDisconnected();
            }
        }
    }
}
