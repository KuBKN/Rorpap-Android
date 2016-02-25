package com.kubkn.rorpap;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.model.User;
import com.kubkn.rorpap.service.HTTPRequest;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.HashMap;

/**
 * Created by batmaster on 2/24/16 AD.
 */
public class FragmentLogin extends Fragment {

    private RorpapApplication app;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        app = (RorpapApplication) getActivity().getApplicationContext();

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
                        User user = new User(response);

                        Toast.makeText(getActivity().getApplicationContext(), user.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        int resourceId = getArguments().getInt(MyActivity.KEY_DRAWABLE_ID);


        return view;
    }

}
