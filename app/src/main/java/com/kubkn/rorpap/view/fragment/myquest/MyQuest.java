package com.kubkn.rorpap.view.fragment.myquest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;
import com.kubkn.rorpap.view.RequestsAdapter;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class MyQuest extends Fragment {

    private RecyclerView recyclerView;
    private RequestsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myquest_myquest, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RorpapApplication app = (RorpapApplication) getActivity().getApplicationContext();
        String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);

        app.getHttpRequest().get("request/get_quest/Reserved/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if ((RequestsAdapter) recyclerView.getAdapter() == null) {
                    adapter = new RequestsAdapter(getActivity(), Request.getLists(response), RequestsAdapter.MY_QUEST);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    ((RequestsAdapter) recyclerView.getAdapter()).addRequests(Request.getLists(response));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
        app.getHttpRequest().get("request/get_quest/Inprogress/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if ((RequestsAdapter) recyclerView.getAdapter() == null) {
                    adapter = new RequestsAdapter(getActivity(), Request.getLists(response), RequestsAdapter.MY_QUEST);
                    recyclerView.setAdapter(adapter);
                }
                else {
                    ((RequestsAdapter) recyclerView.getAdapter()).addRequests(Request.getLists(response));
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

        return view;
    }
}
