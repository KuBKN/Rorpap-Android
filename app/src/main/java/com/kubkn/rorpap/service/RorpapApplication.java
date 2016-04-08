package com.kubkn.rorpap.service;

import android.support.multidex.MultiDexApplication;

import com.kubkn.rorpap.service.gcm.GCMUtilities;

/**
 * Created by batmaster on 2/25/16 AD.
 */
public class RorpapApplication extends MultiDexApplication {

    private HTTPRequest httpRequest;
    private Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        httpRequest = new HTTPRequest(this);
        preferences = new Preferences(this);

        GCMUtilities.register(getApplicationContext());
    }

    public HTTPRequest getHttpRequest() {
        return httpRequest;
    }

    public Preferences getPreferences() {
        return preferences;
    }
}
