package com.kubkn.rorpap.service;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by batmaster on 4/19/16 AD.
 */
public class CheckInprogressBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("broadcc", intent.getAction());
        RorpapApplication app = (RorpapApplication) context;

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            app.checkInprogressQuests("Rorpap has Inprogress quest", "Resume sending location...");
        } else if (action.equals("android.location.PROVIDERS_CHANGED")) {

            ContentResolver contentResolver = context.getContentResolver();
            // Find out what the settings say about which providers are enabled
            int mode = Settings.Secure.getInt(contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);

            if (mode == Settings.Secure.LOCATION_MODE_OFF) {
                if (app.isMyServiceRunning(LocationService.class)) {
                    context.stopService(new Intent(context, LocationService.class));
                }
            } else {
                if (!app.isMyServiceRunning(LocationService.class)) {
                    context.startService(new Intent(context, LocationService.class));
                }

                /*// Get the Mode value from Location system setting
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    // "High accuracy. Uses GPS, Wi-Fi, and mobile networks to determine location";
                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // locationMode = "Device only. Uses GPS to determine location";
                } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    // locationMode = "Battery saving. Uses Wi-Fi and mobile networks to determine location";
                }*/
            }

        } else if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
            app.checkInprogressQuests("Rorpap has Inprogress quest", "Resume sending location...");
        }

    }
}
