package com.kubkn.rorpap.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.model.User;
import com.kubkn.rorpap.service.HTTPRequest;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by batmaster on 2/24/16 AD.
 */
public class FragmentUser extends Fragment {

    private RorpapApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        app = (RorpapApplication) getActivity().getApplicationContext();

        if (app.getPreferences().getString(Preferences.KEY_USERID) == null) {
            View view = inflater.inflate(R.layout.fragment_login, container, false);

            final EditText editTextUsername = (EditText) view.findViewById(R.id.editTextUsername);
            final EditText editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);

            Button buttonLogin = (Button) view.findViewById(R.id.buttonLogin);
            buttonLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("email", editTextUsername.getText().toString());
                    params.put("password", HTTPRequest.md5(editTextPassword.getText().toString()));

                    app.getHttpRequest().post("user/login", params, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            String res = response;

                            User user = new User(response);

                            app.getPreferences().putString(Preferences.KEY_USERID, user.get_id());
//                            ((MainActivity) getActivity()).selectFragment(0);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            return view;
        }
        else {
            View view = inflater.inflate(R.layout.fragment_user, container, false);

            String user_id = app.getPreferences().getString(Preferences.KEY_USERID);

            TextView textViewID = (TextView) view.findViewById(R.id.textViewID);
            textViewID.setText(user_id);

            Button buttonLogout = (Button) view.findViewById(R.id.buttonLogout);
            buttonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    app.getPreferences().remove(Preferences.KEY_USERID);
                }
            });

            return view;
        }
    }

}
