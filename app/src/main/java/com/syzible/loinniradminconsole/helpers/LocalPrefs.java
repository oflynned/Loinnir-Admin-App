package com.syzible.loinniradminconsole.helpers;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by ed on 07/09/2017.
 */

public class LocalPrefs {
    public enum Prefs {
        is_authenticated, username, secret
    }

    public static void logOut(Context context) {
        setPreference(context, Prefs.username, null);
        setPreference(context, Prefs.secret, null);
        setPreference(context, Prefs.is_authenticated, false);
    }

    public static void login(Context context, String username, String secret) {
        setPreference(context, Prefs.username, username);
        setPreference(context, Prefs.secret, secret);
        setPreference(context, Prefs.is_authenticated, true);
    }

    private static void setPreference(Context context, Prefs pref, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(pref.name(), value).apply();
    }

    private static void setPreference(Context context, Prefs pref, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(pref.name(), value).apply();
    }

    private static boolean getBooleanPreference(Context context, Prefs pref) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(pref.name(), false);
    }

    private static String getStringPreference(Context context, Prefs pref) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(pref.name(), null);
    }

    public static boolean isAuthenticated(Context context) {
        return getBooleanPreference(context, Prefs.is_authenticated);
    }

    public static String getUsername(Context context) {
        return getStringPreference(context, Prefs.username);
    }

    public static String getSecret(Context context) {
        return getStringPreference(context, Prefs.secret);
    }
}
