package example.banty.com.instagramclone.listeners;

/*
* Listener for network connectivity changes
* */
public interface NetworkConnectivityListener {
    void onNetworkConnected();

    void onNetworkDisconnected();
}
