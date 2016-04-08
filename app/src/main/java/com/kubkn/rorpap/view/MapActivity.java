package com.kubkn.rorpap.view;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private RorpapApplication app;

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        app = (RorpapApplication) getApplicationContext();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);

        String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);

        app.getHttpRequest().get("request/get_quest/Reserved/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requests = Request.getLists(response);
                for (int i = 0; i < requests.size(); i++) {
                    String[] fromLoc = requests.get(i).getFromLoc().split(", ");
                    double lat = Double.parseDouble(fromLoc[0]);
                    double lng = Double.parseDouble(fromLoc[1]);
                    LatLng from = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(from)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                            .title(requests.get(i).getSender_id()));

                    String[] toLoc = requests.get(i).getToLoc().split(", ");
                    lat = Double.parseDouble(toLoc[0]);
                    lng = Double.parseDouble(toLoc[1]);
                    LatLng to = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(to)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green))
                            .title(requests.get(i).getRecipient_name()));
                }
            }
        });
        app.getHttpRequest().get("request/get_quest/Inprogress/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requests = Request.getLists(response);
                for (int i = 0; i < requests.size(); i++) {
                    String[] fromLoc = requests.get(i).getFromLoc().split(", ");
                    double lat = Double.parseDouble(fromLoc[0]);
                    double lng = Double.parseDouble(fromLoc[1]);
                    LatLng from = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(from)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                            .title(requests.get(i).getSender_id()));

                    String[] toLoc = requests.get(i).getToLoc().split(", ");
                    lat = Double.parseDouble(toLoc[0]);
                    lng = Double.parseDouble(toLoc[1]);
                    LatLng to = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(to)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green))
                            .title(requests.get(i).getRecipient_name()));
                }
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
