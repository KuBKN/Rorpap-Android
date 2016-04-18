package com.kubkn.rorpap.view.fragment.myquest;

import android.app.ProgressDialog;
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
import com.kubkn.rorpap.view.RefreshableActivity;
import com.kubkn.rorpap.view.RefreshableFragment;
import com.kubkn.rorpap.view.RequestsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class History extends RefreshableFragment {

    private RorpapApplication app;
    private ProgressDialog loading;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myquest_history, container, false);

        app = (RorpapApplication) getActivity().getApplicationContext();

        loading = new ProgressDialog(getActivity());
        loading.setTitle("Requests");
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        recyclerView = (RecyclerView) view.findViewById(R.id.history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refresh();

        return view;
    }

    private ArrayList<Request> SortRequest(ArrayList<Request> listToBeSort){
        Collections.sort(listToBeSort, new Comparator<Request>() {
            @Override
            public int compare(Request lhs, Request rhs) {
                return rhs.get_id().compareTo(lhs.get_id());
            }
        });
        return listToBeSort;
    }

    @Override
    public void refresh() {
        loading.show();
        String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);

        app.getHttpRequest().get("request/get_quest/Finished/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                RequestsAdapter adapter = new RequestsAdapter((RefreshableActivity) getActivity(), SortRequest(Request.getLists(response)), RequestsAdapter.MY_QUEST);
                recyclerView.setAdapter(adapter);

                if (loading.isShowing()) {
                    loading.dismiss();
                }
            }
        });
    }
}
