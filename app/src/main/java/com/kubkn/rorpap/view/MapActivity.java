package com.kubkn.rorpap.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private String[] mDrawerTitle = {"Find Request", "Messenger", "Map", "Profile", "Log out"};
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ActionBarDrawerToggle mDrawerToggle;

    private RorpapApplication app;

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        app = (RorpapApplication) getApplicationContext();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mListView = (ListView) findViewById(R.id.drawer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_row, mDrawerTitle);

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(mListView);

                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(getApplicationContext(), FindRequestActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        break;
                    case 3:
                        intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 4:
                        app.getPreferences().remove(Preferences.KEY_USERID);
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }

        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA337")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.actionbar);
        ImageView menu = (ImageView) actionBar.getCustomView().findViewById(R.id.imageViewMenu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mListView);
            }
        });
        ImageView logo = (ImageView) actionBar.getCustomView().findViewById(R.id.imageViewLogo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.738432, 100.530925), 11));

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
                    String[] toLoc = requests.get(i).getToLoc().split(", ");
                    double lat = Double.parseDouble(toLoc[0]);
                    double lng = Double.parseDouble(toLoc[1]);
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
