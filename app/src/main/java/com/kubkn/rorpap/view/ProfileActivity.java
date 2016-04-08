package com.kubkn.rorpap.view;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.User;
import com.kubkn.rorpap.service.HTTPRequest;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private RorpapApplication app;

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextTel;
    private TextView textViewPassword;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private Button buttonUpdate;

    private boolean showEditPassword = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        app = (RorpapApplication) getApplicationContext();

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextTel = (EditText) findViewById(R.id.editTextTel);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditPassword = !showEditPassword;

                if (showEditPassword) {
                    editTextPassword.setVisibility(View.VISIBLE);
                    editTextPasswordConfirm.setVisibility(View.VISIBLE);
                }
                else {
                    editTextPassword.setVisibility(View.GONE);
                    editTextPasswordConfirm.setVisibility(View.GONE);
                }
            }
        });

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);

        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("_id", app.getPreferences().getString(Preferences.KEY_USERID));
                params.put("firstname", editTextFirstName.getText().toString());
                params.put("lastname", editTextLastName.getText().toString());
                params.put("email", editTextEmail.getText().toString());
                params.put("tel", editTextTel.getText().toString());

                if (showEditPassword) {
                    if (editTextPassword.getText().toString().equals(editTextPasswordConfirm.getText().toString())) {
                        params.put("password", HTTPRequest.md5(editTextPassword.getText().toString()));
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwords are not matched!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    params.put("password", "");
                }


                app.getHttpRequest().post("user/update", params, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Profile is updated.", Toast.LENGTH_SHORT).show();

                        showEditPassword = false;
                        editTextPassword.setText("");
                        editTextPassword.setVisibility(View.GONE);
                        editTextPasswordConfirm.setText("");
                        editTextPasswordConfirm.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error updating profile!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        app.getHttpRequest().get("user/get/" + app.getPreferences().getString(Preferences.KEY_USERID), null, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                User user = new User(response);

                editTextFirstName.setText(user.getFirstname());
                editTextLastName.setText(user.getLastname());
                editTextEmail.setText(user.getEmail());
                editTextTel.setText(user.getTel());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error loading profile!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
