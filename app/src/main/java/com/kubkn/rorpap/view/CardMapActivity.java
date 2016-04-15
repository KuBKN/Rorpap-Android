package com.kubkn.rorpap.view;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.ArrayList;

public class CardMapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private RorpapApplication app;

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    private ArrayList<String> requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_map);

        app = (RorpapApplication) getApplicationContext();

        requests = new ArrayList<String>();

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                requests = null;
            }
            else{
                requests = extras.getStringArrayList("Request locations");
            }
        }
        else{
            requests = (ArrayList<String>) savedInstanceState.getSerializable("Request locations");
        }

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.738432, 100.530925), 11));

        String[] fromLocs = requests.get(0).split(", ");
        String[] toLocs = requests.get(1).split(", ");
        String senderID = requests.get(2);
        String recipientName = requests.get(3);

        double lat;
        double lng;

        lat = Double.parseDouble(fromLocs[0]);
        lng = Double.parseDouble(fromLocs[1]);
        LatLng from = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions()
                .position(from)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red))
                .title(senderID));

        lat = Double.parseDouble(toLocs[0]);
        lng = Double.parseDouble(toLocs[1]);
        LatLng to = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions()
                .position(to)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_green))
                .title(recipientName));
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
