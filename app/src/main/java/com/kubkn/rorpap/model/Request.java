package com.kubkn.rorpap.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by batmaster on 3/30/16 AD.
 */
public class Request {
    private String _id;
    private String sender_id;
    private String messenger_id;

    private String recipient_name;
    private String recipient_email;
    private String recipient_tel;
    private String size_length;
    private String size_width;
    private String size_height;
    private String weight;

    private String type;
    private boolean hasAccept;
    private String fromLoc;
    private String toLoc;
    private String reqLimitDate;
    private String reqLimitTime;
    private String shipLimitDate;
    private String shipLimitHour;
    private String shipLimitTime;
    private String price;
    private String comment;

    public Request(String jsonString) {
        try {
            JSONArray ja = new JSONArray(jsonString);
            JSONObject jo = ja.getJSONObject(0);
            load(jo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Request(JSONObject jsonObject) {
        load(jsonObject);
    }

    private void load(JSONObject jo) {
        Log.d("_Request", jo.toString());

        try {
            _id = jo.has("_id") ? jo.getString("_id") : "";
            sender_id = jo.has("_id") ? jo.getString("_id") : "";
            messenger_id = jo.has("messenger_id") ? jo.getString("messenger_id") : "";

            recipient_name = jo.has("recipient_name") ? jo.getString("recipient_name") : "";
            recipient_email = jo.has("recipient_email") ? jo.getString("recipient_email") : "";
            recipient_tel = jo.has("recipient_tel") ? jo.getString("recipient_tel") : "";
            size_length = jo.has("size_length") ? jo.getString("size_length") : "";
            size_width = jo.has("size_width") ? jo.getString("size_width") : "";
            size_height = jo.has("size_height") ? jo.getString("size_height") : "";
            weight = jo.has("weight") ? jo.getString("weight") : "";

            type = jo.has("type") ? jo.getString("type") : "";
            hasAccept = jo.has("hasAccept") ? jo.getBoolean("hasAccept") : false;
            fromLoc = jo.has("fromLoc") ? jo.getString("fromLoc") : "";
            toLoc = jo.has("toLoc") ? jo.getString("toLoc") : "";
            reqLimitDate = jo.has("reqLimitDate") ? jo.getString("reqLimitDate") : "";
            reqLimitTime = jo.has("reqLimitTime") ? jo.getString("reqLimitTime") : "";
            shipLimitDate = jo.has("shipLimitDate") ? jo.getString("shipLimitDate") : "";
            shipLimitHour = jo.has("shipLimitHour") ? jo.getString("shipLimitHour") : "";
            shipLimitTime = jo.has("shipLimitTime") ? jo.getString("shipLimitTime") : "";
            price = jo.has("price") ? jo.getString("price") : "";
            comment = jo.has("comment") ? jo.getString("comment") : "";

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Request> getLists(String jsonString) {
        ArrayList<Request> lists = new ArrayList<Request>();

        try {
            JSONArray ja = new JSONArray(jsonString);
            for (int i = 0; i < ja.length(); i ++) {
                lists.add(new Request(ja.getJSONObject(i)));
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

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessenger_id() {
        return messenger_id;
    }

    public void setMessenger_id(String messenger_id) {
        this.messenger_id = messenger_id;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getRecipient_tel() {
        return recipient_tel;
    }

    public void setRecipient_tel(String recipient_tel) {
        this.recipient_tel = recipient_tel;
    }

    public String getSize_length() {
        return size_length;
    }

    public void setSize_length(String size_length) {
        this.size_length = size_length;
    }

    public String getSize_width() {
        return size_width;
    }

    public void setSize_width(String size_width) {
        this.size_width = size_width;
    }

    public String getSize_height() {
        return size_height;
    }

    public void setSize_height(String size_height) {
        this.size_height = size_height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHasAccept() {
        return hasAccept;
    }

    public void setHasAccept(boolean hasAccept) {
        this.hasAccept = hasAccept;
    }

    public String getFromLoc() {
        return fromLoc;
    }

    public void setFromLoc(String fromLoc) {
        this.fromLoc = fromLoc;
    }

    public String getToLoc() {
        return toLoc;
    }

    public void setToLoc(String toLoc) {
        this.toLoc = toLoc;
    }

    public String getReqLimitDate() {
        return reqLimitDate;
    }

    public void setReqLimitDate(String reqLimitDate) {
        this.reqLimitDate = reqLimitDate;
    }

    public String getReqLimitTime() {
        return reqLimitTime;
    }

    public void setReqLimitTime(String reqLimitTime) {
        this.reqLimitTime = reqLimitTime;
    }

    public String getShipLimitDate() {
        return shipLimitDate;
    }

    public void setShipLimitDate(String shipLimitDate) {
        this.shipLimitDate = shipLimitDate;
    }

    public String getShipLimitHour() {
        return shipLimitHour;
    }

    public void setShipLimitHour(String shipLimitHour) {
        this.shipLimitHour = shipLimitHour;
    }

    public String getShipLimitTime() {
        return shipLimitTime;
    }

    public void setShipLimitTime(String shipLimitTime) {
        this.shipLimitTime = shipLimitTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
