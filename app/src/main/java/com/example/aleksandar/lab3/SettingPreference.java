package com.example.aleksandar.lab3;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

/**
 * Created by Aleksandar on 10.12.2016.
 */

public class SettingPreference {
    private static final String PREF_IS_MAP_ON = "isMapOn";
    private static final String PREF_CURRENT_LAT = "currentLat";
    private static final String PREF_CURRENT_LONG = "currentLong";

    public static boolean isMapOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_MAP_ON, false);
    }

    public static void setMapOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_MAP_ON, isOn)
                .apply();
    }
}
