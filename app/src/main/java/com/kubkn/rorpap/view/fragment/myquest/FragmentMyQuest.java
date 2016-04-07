package com.kubkn.rorpap.view.fragment.myquest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.Response;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.ArrayList;

/**
 * Created by batmaster on 2/24/16 AD.
 */
public class FragmentMyQuest extends Fragment {

    private static boolean hadCreatedTabHost = false;

    private MyQuestPagerAdapter myQuestPagerAdapter;
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        View view = inflater.inflate(R.layout.fragment_myquest, container, false);

        pager = (ViewPager) view.findViewById(R.id.pager);
        myQuestPagerAdapter = new MyQuestPagerAdapter(getChildFragmentManager());
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

        if (!hadCreatedTabHost) {
            for (int i = 0; i < myQuestPagerAdapter.getCount(); i++) {
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(myQuestPagerAdapter.getTag(i))
                                .setTabListener(tabListener));
            }
        }

        return view;
    }

    private class MyQuestPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment[] fragments = {
                new FindRequest(),
                new MyQuest(),
                new History()
        };

        private String[] tags = {
                "Find Request", "My Quest", "History"
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
    }
}
