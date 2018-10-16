package mobile.a3tech.com.a3tech.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class PermissionsStuffs {
    public static final String[] INITIAL_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static final String[] CALL_PHONE={
            Manifest.permission.CALL_PHONE
    };
    public static final String[] CAMERA_PERMS={
            Manifest.permission.CAMERA
    };
    public static final String[] CONTACTS_PERMS={
            Manifest.permission.READ_CONTACTS
    };
    public static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static final int INITIAL_REQUEST=1337;
    public static final int ICALL_PHONE_REQUEST=13327;
    public static final int CAMERA_REQUEST=INITIAL_REQUEST+1;
    public static final int CONTACTS_REQUEST=INITIAL_REQUEST+2;
    public static final int LOCATION_REQUEST=INITIAL_REQUEST+3;
    public static boolean canAccessLocation(Context context) {
        return(hasPermission(context,Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public static boolean canAccessCamera(Context context) {
        return(hasPermission(context,Manifest.permission.CAMERA));
    }

    public static boolean canAccessContacts(Context context) {
        return(hasPermission(context,Manifest.permission.READ_CONTACTS));
    }

    public static boolean hasPermission(Context context,String perm) {
        return(PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context, perm));
    }
}
