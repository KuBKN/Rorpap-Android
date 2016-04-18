package com.kubkn.rorpap.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

public class LogoActivity extends AppCompatActivity {

    private RorpapApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_logo);

        app = (RorpapApplication) getApplicationContext();

        final Intent intent;
        if (app.getPreferences().getString(Preferences.KEY_USERID) == null)
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        else
            intent = new Intent(getApplicationContext(), MyQuestActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}
