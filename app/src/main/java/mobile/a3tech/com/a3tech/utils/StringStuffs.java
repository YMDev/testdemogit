package mobile.a3tech.com.a3tech.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

/**
 * Created by SPIA on 11/04/2017.
 */

public class StringStuffs {
    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length();) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static boolean isEmpty(String value){
        return value == null || value.length() == 0;
    }

    public static String getAdresseFromGpsLocation(Double latitude, Double longitude, Context context){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            return addresses.get(0).getAddressLine(0); // adresse
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDistanceFromTech(String lat1, String lon1, String lat2, String lon2){
        Location loc1 = new Location("");
        loc1.setLatitude(Double.valueOf(lat1));
        loc1.setLongitude(Double.valueOf(lon1));

        Location loc2 = new Location("");
        loc2.setLatitude(Double.valueOf(lat2));
        loc2.setLongitude(Double.valueOf(lon2));

        float distanceInMeters = loc1.distanceTo(loc2);
        return String.valueOf(distanceInMeters);
    }
}
