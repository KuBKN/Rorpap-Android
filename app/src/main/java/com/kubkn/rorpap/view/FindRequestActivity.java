package com.kubkn.rorpap.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

/**
 * Created by knotsupavit on 11-Apr-16.
 */
public class FindRequestActivity extends AppCompatActivity {
    private String[] mDrawerTitle = {"Find Request", "Messenger", "Map", "Profile", "Log out"};
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ActionBarDrawerToggle mDrawerToggle;

    private RorpapApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
                        break;
                    case 1:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        intent = new Intent(getApplicationContext(), MapActivity.class);
                        startActivity(intent);
                        finish();
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
        //TODO: implement 1. show all requests 2. search requests

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
