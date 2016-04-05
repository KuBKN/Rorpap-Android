package com.kubkn.rorpap.service.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.gms.gcm.GcmListenerService;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.LocationService;

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

            String type = data.getString("type");
            Log.d("pushno11", type);
            if (type.equals("0")) {
                String signal = data.getString("signal");

                if (signal.equals("101")) {

                    Log.d("pushno12", signal);

                    startService(new Intent(this, LocationService.class));

                    noti("รับคำสั่งมาแล้วครับ", "จะเริ่มส่งตำแหน่ง GPS ไปนะ :)");

                } else if (signal.equals("102")) {
                    stopService(new Intent(this, LocationService.class));

                    noti("รับคำสั่งมาแล้วครับ", "หยุดส่ง GPS จ้า");
                }
            }
            else if (type.equals("1")) {

            }

//            String notification = data.getString("notification", null);
//            if (notification != null) {
//
//                try {
//                    JSONObject jo = new JSONObject(notification);
//
//                    int code = jo.getInt("code");
//
//                    // start to send gps location
//                    if (code == 101) {
//                        String requestId = jo.getString("requestId");
//
//                    }
//
//
//
//                } catch (JSONException e) {
//
//                }
//
//            }
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
