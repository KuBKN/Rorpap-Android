package com.kubkn.rorpap.service.gcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GcmListenerService;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.LocationService;
import com.kubkn.rorpap.service.RorpapApplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by batmaster on 2/25/16 AD.
 */
public class GCMNotificationIntentService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        if (!data.isEmpty()) {
            Log.d("pushno", from);
            Log.d("pushno", data.toString());

            RorpapApplication app = (RorpapApplication) getApplicationContext();

            String type = data.getString("type");
            String signal = data.getString("signal");
            String title = data.getString("title");
            String content = data.getString("content");

            if (type.equals("0")) {
                if (signal.equals("101")) {
                    if (!app.isMyServiceRunning(LocationService.class)) {
                        startService(new Intent(this, LocationService.class));
                    }
                } else if (signal.equals("102")) {
                    if (app.isMyServiceRunning(LocationService.class)) {
                        stopService(new Intent(this, LocationService.class));
                    }
                }

                if (!title.equals("")) {
                    noti(title, content);
                }
            }


        }
    }

    private void noti(String title, String text) {
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setAutoCancel(true)
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }
}
