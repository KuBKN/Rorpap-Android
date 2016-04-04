package com.kubkn.rorpap.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by batmaster on 2/25/16 AD.
 */
public class HTTPRequest {

    private Context context;
    private static RequestQueue requestQueue;

    private String BASE_URL = "http://188.166.180.204:8080/api/";

    public HTTPRequest(Context context) {
        this.context = context;

        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);

    }

    /**
     * Send GET request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     */
    public void get(String path, HashMap<String, String> params) {
        get(path, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /**
     * Send GET request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     * @param callback Response.Listener callback action after getting response
     */
    public void get(String path, HashMap<String, String> params, Response.Listener callback) {
        get(path, params, callback, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("erro", error.getMessage());
            }
        });
    }

    /**
     * Send GET request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     * @param callback Response.Listener callback action after getting response
     * @param errorCallback Response.ErrorListener callback action if response error
     */
    public void get(String path, HashMap<String, String> params, Response.Listener<String> callback, Response.ErrorListener errorCallback) {
        request(Request.Method.GET, path, params, callback, errorCallback);
    }

    /**
     * Send POST request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     */
    public void post(String path, HashMap<String, String> params) {
        post(path, params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /**
     * Send POST request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     * @param callback Response.Listener callback action after getting response
     */
    public void post(String path, HashMap<String, String> params, Response.Listener callback) {
        post(path, params, callback, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    /**
     * Send POST request.
     * @param path the api path
     * @param params HashMap<String, String> parameters
     * @param callback Response.Listener callback action after getting response
     * @param errorCallback Response.ErrorListener callback action if response error
     */
    public void post(String path, HashMap<String, String> params, Response.Listener<String> callback, Response.ErrorListener errorCallback) {
        request(Request.Method.POST, path, params, callback, errorCallback);
    }

    private void request(int method, String path, HashMap<String, String> params, Response.Listener<String> callback, Response.ErrorListener errorCallback) {

        StringRequest req = new StringRequest(method, BASE_URL + path, callback, errorCallback);
        req.setParams(params);

        Log.d("_HTTPRequest", req.toString());

        requestQueue.add(req);
    }

    /**
     * Encrypt string to md5.
     * @param s encrypting string
     * @return md5 string
     */
    public static String md5(String s) {
        MessageDigest digest;
        try
        {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "x", bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
