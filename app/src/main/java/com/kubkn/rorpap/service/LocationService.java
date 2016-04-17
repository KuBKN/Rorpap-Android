package com.kubkn.rorpap.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by batmaster on 3/31/16 AD.
 */
public class LocationService extends Service {

    public static int R = 1;

    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("pushno loc", "onStartCommand");


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60 * 1000, 1, listener);

        Toast.makeText(getApplicationContext(), "GPS onStartCommand", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "GPS onCreate", Toast.LENGTH_SHORT).show();
        Log.d("pushno loc", "onCreate");

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        listener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                RorpapApplication app = (RorpapApplication) getApplicationContext();

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", app.getPreferences().getString(Preferences.KEY_USERID));
                params.put("date", new Date().toString());
                params.put("location", location.getLatitude() + "," + location.getLongitude());

                Log.d("pushno loc", "round " + R++);

                app.getHttpRequest().post("tracking/update", params, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Update Location OK", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(getApplicationContext(), "Status Change To: " + status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), "GPS is disabled", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(listener);
        Toast.makeText(getApplicationContext(), "GPS onDestroy", Toast.LENGTH_SHORT).show();
        Log.d("pushno loc", "onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "GPS onBind", Toast.LENGTH_SHORT).show();
        Log.d("pushno loc", "onBind");
        return null;
    }
}
