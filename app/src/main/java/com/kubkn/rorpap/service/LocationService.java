package com.kubkn.rorpap.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.model.User;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by batmaster on 3/31/16 AD.
 */
public class LocationService extends IntentService {

    public LocationService() {
        super(LocationService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3*1000, 10, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                RorpapApplication app = (RorpapApplication) getApplicationContext();

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("user_id", app.getPreferences().getString(Preferences.KEY_USERID));
                params.put("date", new Date().toString());
                params.put("location", location.getLatitude() + "," + location.getLongitude());

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
                Toast.makeText(getApplicationContext(), "GPS is disabled" , Toast.LENGTH_SHORT).show();
            }
        });

    }
}
