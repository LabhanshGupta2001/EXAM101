package com.dollop.exam101.Basics.UtilityTools;

import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Anil on 9/4/2021.
 */
public class SavedData {
    private static final String FirebaseToken = "FirebaseToken";
    private static final String Language = "Language";
    private static final String ReferralCode = "ReferralCode";
    private static final String popUp = "popUp";
    private static SharedPreferences prefs;
    private static final String latitude = "latitude";
    private static final String longitude = "longitude";
    private static final String CountryKey = "CountryKey";
    private static final String CountryId = "CountryId";
    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }
    public static String getLanguage() {
        return getInstance().getString(Language, Constants.Key.english);
    }
    public static void saveLanguage(String role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(Language, role);
        editor.apply();
    }   public static boolean getPopUp() {
        return getInstance().getBoolean(popUp, false);
    }
    public static void savePopUp(boolean role) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(popUp, role);
        editor.apply();
    }
    public static String getFirebaseToken() {
        return getInstance().getString(FirebaseToken, Constants.Key.blank);
    }
    public static void saveFirebaseToken(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(FirebaseToken, token);
        editor.apply();
    }
    public static String getCountryKey() {
        return getInstance().getString(CountryKey, Constants.Key.blank);
    }

    public static void saveCountryKey(String key) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CountryKey, key);
        editor.apply();
    }

    public static String getReferralCode() {
        return getInstance().getString(ReferralCode, Constants.Key.blank);
    }
    public static void saveReferralCode(String token) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(ReferralCode, token);
        editor.apply();
    }
    public static String getCountryId() {
        return getInstance().getString(CountryId, Constants.Key.Country_Id);
    }
    public static void saveCountryId(String Id) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(CountryId, Id);
        editor.apply();
    }

    @NonNull
    public static LatLng getCurrentLocation() {
        double lat = Double.parseDouble(getInstance().getString(latitude, "0.0"));
        double longi = Double.parseDouble(getInstance().getString(longitude,"0.0"));
        return new LatLng(lat,longi);
    }
    public static void saveCurrentLocation(Location location) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putString(latitude, String.valueOf(location.getLatitude()));
        editor.putString(longitude, String.valueOf(location.getLongitude()));
        editor.apply();
    }
}