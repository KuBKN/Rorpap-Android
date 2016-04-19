package com.kubkn.rorpap.service;

import android.app.ActivityManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.gcm.GCMUtilities;

import java.util.ArrayList;
import java.util.HashMap;

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

        registerGCM();

        checkInprogressQuests("Rorpap has Inprogress quest", "Resume sending location...");
    }

    public HTTPRequest getHttpRequest() {
        return httpRequest;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void registerGCM() {
        Log.d("GCMs", "regis");
        GCMUtilities.register(getApplicationContext());
    }

    public void checkInprogressQuests(final String title, final String content) {
        getHttpRequest().get("request/get_quest/Inprogress/" + getPreferences().getString(Preferences.KEY_USERID), null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requests = Request.getLists(response);
                if (requests.size() > 0) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("type", "0");
                    params.put("signal", "101");
                    params.put("title", title);
                    params.put("content", content);
                    params.put("user_id", getPreferences().getString(Preferences.KEY_USERID));
                    getHttpRequest().post("gcm/push/", params, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Rorpap has in progress quest, start sending location...", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
            }
        });
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
