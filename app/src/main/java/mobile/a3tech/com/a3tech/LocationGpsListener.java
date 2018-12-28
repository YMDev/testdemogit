package mobile.a3tech.com.a3tech;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;

public final class LocationGpsListener implements LocationListener {

    public boolean isGPSEnabled = false;

    private Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    private static final long MIN_TIME_BW_UPDATES = 0;

    protected LocationManager locationManager;
    private String provider;
    private Context context;

    public LocationGpsListener(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 && !PermissionsStuffs.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) && !PermissionsStuffs.hasPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            Log.d("KKKKK", "Location not available");
            //Location not available
        }
    }

    public void stopLocationUpdates() {
        if (locationManager != null) {
            locationManager.removeUpdates(LocationGpsListener.this);
        }
    }

    public double getLatitude() {
        return location != null ? location.getLatitude() : 0;
    }

    public double getLongitude() {
        return location != null ? location.getLongitude() : 0;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("KKKKK", "onLocationChanged ");
        this.location = location;
        final A3techUser connectedUser = PreferencesValuesUtils.getConnectedUser(context);
        connectedUser.setLatitude(location.getLatitude());
        connectedUser.setLongetude(location.getLongitude());
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(A3techHomeActivity.TAG_CONNECTED_USER_LATITUDE, String.valueOf(location.getLatitude()));
        editor.putString(A3techHomeActivity.TAG_CONNECTED_USER_LONGETUDE, String.valueOf(location.getLongitude()));
        editor.commit();
        UserManager.getInstance().updateUser(connectedUser, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                PreferencesValuesUtils.setConnectedUser(context,connectedUser);
            }
            @Override
            public void dataLoadingError(int errorCode) {

            }
        });
    }

    @Override
    public void onProviderDisabled(String provider) {
        // NO-OP
    }

    @Override
    public void onProviderEnabled(String provider) {
        // NO-OP
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // NO-OP
    }

}