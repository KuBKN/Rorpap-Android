package com.kubkn.rorpap.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by batmaster on 2/25/16 AD.
 */
public class User {

    private String jsonString;

    private String _id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String dateOfBirth;
    private int status;
    private int point;

    public User(String jsonString) {
        Log.d("_User", jsonString);

        this.jsonString = jsonString;

        try {
            JSONArray ja = new JSONArray(jsonString);
            JSONObject jo = ja.getJSONObject(0);
            _id = jo.getString("_id");
            firstname = jo.has("firstname") ? jo.getString("firstname") : "";
            lastname = jo.has("lastname") ? jo.getString("lastname") : "";
            email = jo.has("email") ? jo.getString("email") : "";
            password = jo.has("password") ? jo.getString("password") : "";
            dateOfBirth = jo.has("dateOfBirth") ? jo.getString("dateOfBirth") : "";
            status = jo.has("status") ? jo.getInt("status") : -1;
            point = jo.has("point") ? jo.getInt("point") : -1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String get_id() {
        return _id;
    }

//    public void set_id(String _id) {
//        this._id = _id;
//    }

    public String getFirstname() {
        return firstname;
    }

//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }

    public String getLastname() {
        return lastname;
    }

//    public void setLastname(String lastname) {
//        this.lastname = lastname;
//    }

    public String getEmail() {
        return email;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }

    public int getStatus() {
        return status;
    }

//    public void setStatus(int status) {
//        this.status = status;
//    }

    public int getPoint() {
        return point;
    }

//    public void setPoint(int point) {
//        this.point = point;
//    }

}
