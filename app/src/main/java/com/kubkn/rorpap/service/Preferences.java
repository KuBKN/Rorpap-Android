package com.kubkn.rorpap.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by batmaster on 3/2/16 AD.
 */
public class Preferences {

    private final static String PREF_KEY = "rorpap";

    public final static String KEY_USERID = "KEY_USERID";
    public final static String KEY_GCM_TOKEN = "KEY_GCM_TOKEN";

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public Preferences(Context context) {
        sp = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sp.getString(key, null);
    }

}
