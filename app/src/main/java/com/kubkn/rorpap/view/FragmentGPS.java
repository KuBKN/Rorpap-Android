package com.kubkn.rorpap.view;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kubkn.rorpap.R;

import org.w3c.dom.Text;

/**
 * Created by batmaster on 2/24/16 AD.
 */
public class FragmentGPS extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gps, container, false);

        final TextView textViewGPS = (TextView) view.findViewById(R.id.textViewGPS);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3*1000, 10, new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                textViewGPS.setText(location.getLatitude() + " " + location.getLongitude());
                Log.d("pushno locGPS", "round ");
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(getActivity().getApplicationContext(), "Status Change To: " + status , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getActivity().getApplicationContext(), "GPS is disabled" , Toast.LENGTH_SHORT).show();
            }
        });

//        int resourceId = getArguments().getInt(MyActivity.KEY_DRAWABLE_ID);


        return view;
    }

}
