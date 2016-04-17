package com.kubkn.rorpap.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by batmaster on 3/30/16 AD.
 */
public class Acceptance {
    private String _id;
    private String request_id;
    private String messenger_id;

    private String date;
    private String hour;
    private String min;

    private JSONObject jsonObject;

    public Acceptance(String jsonString) {
        try {
            JSONArray ja = new JSONArray(jsonString);
            JSONObject jo = ja.getJSONObject(0);
            jsonObject = jo;
            load(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Acceptance(JSONObject jo) {
        jsonObject = jo;
        load(jo);
    }

    private void load(JSONObject jo) {
        Log.d("_Acceptance", jo.toString());

        try {
            _id = jo.has("_id") ? jo.getString("_id") : "";
            request_id = jo.has("request_id") ? jo.getString("request_id") : "";
            messenger_id = jo.has("messenger_id") ? jo.getString("messenger_id") : "";

            date = jo.has("date") ? jo.getString("date") : "";
            hour = jo.has("hour") ? jo.getString("hour") : "";
            min = jo.has("min") ? jo.getString("min") : "";

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Acceptance> getLists(String jsonString) {
        ArrayList<Acceptance> lists = new ArrayList<Acceptance>();

        try {
            JSONArray ja = new JSONArray(jsonString);
            for (int i = 0; i < ja.length(); i ++) {
                lists.add(new Acceptance(ja.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lists;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getMessenger_id() {
        return messenger_id;
    }

    public void setMessenger_id(String messenger_id) {
        this.messenger_id = messenger_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }


    public String toString(){
        String resultString = "_id: " + get_id() + " request_id: " + getRequest_id() + " messenger_id: " + getMessenger_id();
        return resultString;
    }

    public JSONObject getJsonObject(){
        return jsonObject;
    }
}
