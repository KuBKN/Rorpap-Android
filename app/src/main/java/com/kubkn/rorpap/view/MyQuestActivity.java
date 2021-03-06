package com.kubkn.rorpap.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.kubkn.rorpap.R;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;
import com.kubkn.rorpap.view.fragment.myquest.AcceptedQuest;
import com.kubkn.rorpap.view.fragment.myquest.History;
import com.kubkn.rorpap.view.fragment.myquest.InProgress;

public class MyQuestActivity extends RefreshableActivity {

    private String[] mDrawerTitle = {"Find Request", "Messenger", "Map", "Profile", "Log out"};
    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private ActionBarDrawerToggle mDrawerToggle;

    private RorpapApplication app;

    private MyQuestPagerAdapter myQuestPagerAdapter;
    private SwipeRefreshLayout refreshLayout;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

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

        pager = (ViewPager) findViewById(R.id.pager);
        myQuestPagerAdapter = new MyQuestPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(myQuestPagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        for (int i = 0; i < myQuestPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(myQuestPagerAdapter.getTag(i))
                            .setTabListener(tabListener));
        }

        pager.setOffscreenPageLimit(3);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setSwipeRefreshEnable(boolean enable) {
        refreshLayout.setEnabled(enable);
    }

    @Override
    public void refresh() {
        myQuestPagerAdapter.refresh();
        refreshLayout.setRefreshing(false);
    }

    private class MyQuestPagerAdapter extends FragmentStatePagerAdapter {

        private RefreshableFragment[] fragments = {
                new AcceptedQuest(),
                new InProgress(),
                new History()
        };

        private String[] tags = {
                "ACCEPTED", "IN PROGRESS", "HISTORY"
        };

        public MyQuestPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments[position];
        }

        public String getTag(int position) {
            return tags[position];
        }

        public void refresh() {
            for (int i = 0; i < fragments.length; i++) {
                fragments[i].refresh();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Close")
                .setMessage("Do you want to close?")
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("Back", null).show();
    }
}
