package mobile.a3tech.com.a3tech.update;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {
    /**
     * Check if a network is available
     */
    public static boolean isAvailable(Context context) {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                connected = ni.isConnected();
            }
        }
        return connected;
    }
    /**
     * Log connection error
     */
    public static void logConnectionError() {
    }
}
