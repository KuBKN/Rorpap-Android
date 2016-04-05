package com.kubkn.rorpap.view;

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

        for (int i = 0; i < myQuestPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(myQuestPagerAdapter.getTag(i))
                            .setTabListener(tabListener));
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






    public static class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

        private ArrayList<Request> requests;

        public RequestsAdapter(ArrayList<Request> requests) {
            this.requests = requests;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_request, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            holder.imageView
            holder.textViewReceiver.setText(requests.get(position).getRecipient_name());
            holder.textViewXMessenger.setText("whereToGetFrom?");
            holder.textViewDueDate.setText(requests.get(position).getShipLimitDate());
            holder.textViewDueTime.setText(requests.get(position).getShipLimitTime());
            holder.textViewEmail.setText(requests.get(position).getRecipient_email());
            holder.textViewPrice.setText(requests.get(position).getPrice());
            holder.textViewTel.setText(requests.get(position).getRecipient_tel());
            holder.textViewDue.setText(requests.get(position).getReqLimitDate() + " " + requests.get(position).getReqLimitTime());
            holder.textViewComment.setText(requests.get(position).getComment());
        }

        @Override
        public int getItemCount() {
            return requests.size();
        }


        public static class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;

            public TextView textViewReceiver;
            public TextView textViewXMessenger;
            public TextView textViewDueDate;
            public TextView textViewDueTime;
            public TextView textViewEmail;
            public TextView textViewPrice;
            public TextView textViewTel;
            public TextView textViewDue;
            public TextView textViewComment;

            public ViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.imageView);
                textViewReceiver = (TextView) view.findViewById(R.id.textViewReceiver);
                textViewXMessenger = (TextView) view.findViewById(R.id.textViewXMessenger);
                textViewDueDate = (TextView) view.findViewById(R.id.textViewDueDate);
                textViewDueTime = (TextView) view.findViewById(R.id.textViewDueTime);
                textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
                textViewPrice = (TextView) view.findViewById(R.id.textViewPrice);
                textViewTel = (TextView) view.findViewById(R.id.textViewTel);
                textViewDue = (TextView) view.findViewById(R.id.textViewDue);
                textViewComment = (TextView) view.findViewById(R.id.textViewComment);
            }
        }
    }



    public static class FindRequest extends Fragment {

        private RecyclerView recyclerView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_myquest_findrequest, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            RorpapApplication app = (RorpapApplication) getActivity().getApplicationContext();
            String sender_id = app.getPreferences().getString(Preferences.KEY_USERID);

            app.getHttpRequest().get("request/get_request/Pending/!" + sender_id, null, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    RequestsAdapter adapter = new RequestsAdapter(Request.getLists(response));
                    recyclerView.setAdapter(adapter);
                }
            });

            return view;
        }
    }

    public static class MyQuest extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_myquest_myquest, container, false);

            return view;
        }
    }

    public static class History extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_myquest_history, container, false);

            return view;
        }
    }



}
