package mobile.a3tech.com.a3tech.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import mobile.a3tech.com.a3tech.activity.A3techDisplayMissionActivity;
import mobile.a3tech.com.a3tech.model.A3techUser;

public class PreferencesValuesUtils {


    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IDENTIFIANT = "identifiant";
    public static final String KEY_CONNECTED_USER_LATITUDE = "UserConnectedLatitude";
    public static final String KEY_CONNECTED_USER_LONGETUDE = "UserConnectedLongetude";
    public static final String KEY_CONNECTED_USER_EMAIL = "MyCredentials";
    public static final String KEY_CONNECTED_USER_NAME = "nomPrenom";
    public static final String KEY_CONNECTED_USER_GSON = "gsonuser";

    public static String getPreferenceStringByParam(Activity context, String paramKey, String defaultVal) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getString(paramKey, defaultVal);
    }

    public static A3techUser getConnectedUser(Activity context) {
        String jsonuserConnected = PreferencesValuesUtils.getPreferenceStringByParam(context, PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, "");
        return new Gson().fromJson(jsonuserConnected, A3techUser.class);
    }

    public static Long getPreferenceLongByParam(Activity context, String paramKey, Long defaultVal) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getLong(paramKey, defaultVal);
    }

    public static Integer getPreferenceIntegerByParam(Activity context, String paramKey, Integer defaultVal) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return prefs.getInt(paramKey, defaultVal);
    }

}
