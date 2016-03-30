package com.kubkn.rorpap.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by batmaster on 2/25/16 AD.
 */
public class GCMNotificationIntentService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (!data.isEmpty()) {

            String notification = data.getString("notification", null);
            if (notification != null) {

                try {
                    JSONObject jo = new JSONObject(notification);

                    int code = jo.getInt("code");

                    // start to send gps location
                    if (code == 101) {
                        String requestId = jo.getString("requestId");

                    }



                } catch (JSONException e) {

                }

            }
        }
    }
}
