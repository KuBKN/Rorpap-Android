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
            firstname = jo.getString("firstname");
            lastname = jo.getString("lastname");
            email = jo.getString("email");
            password = jo.getString("password");
            dateOfBirth = jo.getString("dateOfBirth");
            status = jo.getInt("status");
            point = jo.getInt("point");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public String toString() {
        return jsonString;
    }
}
