package com.kubkn.rorpap.service.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.HTTPRequest;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by batmaster on 3/31/16 AD.
 */
public class GCMUtilities {
    public static void register(final Context context) {

        new AsyncTask<Void, Void, String>() {

            private GoogleCloudMessaging gcmObj;
            private String regId;

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(context);
                    }
                    regId = gcmObj.register("83591773697");
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(context, regId);
                    Toast.makeText(context, "Registered with GCM Server successfully.nn" + msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Reg ID Creation Failed.nnEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time." + msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
    }

    private static void storeRegIdinSharedPref(final Context context, String regId) {
        RorpapApplication app = (RorpapApplication) context;

        app.getPreferences().putString(Preferences.KEY_GCM_TOKEN, regId);

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("user_id", app.getPreferences().getString(Preferences.KEY_USERID));
        params.put("token", regId);

        app.getHttpRequest().post("gcm/register/", params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("savess", "ok: " + response);
                Toast.makeText(context, "Register GCM ok.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("savess", "failed: " + error);
                Toast.makeText(context, "Register GCM failed.", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
    }
}
