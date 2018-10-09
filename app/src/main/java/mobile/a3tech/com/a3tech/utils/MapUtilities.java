package mobile.a3tech.com.a3tech.utils;

import android.content.Context;
import android.content.res.Resources;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MapUtilities {
    public static LatLngBounds getBoundsFromMarkers(List<Marker> markers) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        return bounds;
    }



    public static List<Marker> getMarkersFromListLatLng(GoogleMap googleMap, List<LatLng> latLngs) {
        List<Marker> markers = new ArrayList<>();
        LatLng latLng;
        Marker marker;
        for (LatLng mLatLng : latLngs) {
            latLng = new LatLng(mLatLng.latitude, mLatLng.longitude);
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
            marker.setVisible(false);
            markers.add(marker);
        }
        return markers;
    }


    public static List<String> getAddressFromLatLng(Context mContext, LatLng mLatLng){
        Geocoder geocoder;
        List<android.location.Address> addresses = null;
        List<String> mAddress = new ArrayList<>();

        geocoder = new Geocoder(mContext, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(mLatLng.latitude, mLatLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses != null && addresses.size() > 0) {
            final android.location.Address addr = addresses.get(0);
            //  address
            mAddress.add(addr.getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            // city
            mAddress.add(addr.getLocality());
            // state
            //mAddress.add(addresses.get(0).getAdminArea());

            // country
            mAddress.add(addr.getCountryName());
        }

        // postalCode
        //mAddress.add(addresses.get(0).getPostalCode());
        // knownName
        //mAddress.add(addresses.get(0).getFeatureName()); // Only if available else return NULL

        return mAddress;
    }

}
