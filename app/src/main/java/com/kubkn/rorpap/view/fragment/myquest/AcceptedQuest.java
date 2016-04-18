package com.kubkn.rorpap.view.fragment.myquest;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class AcceptedQuest extends RefreshableFragment {

    private RorpapApplication app;
    private ProgressDialog loading;

    private RecyclerView recyclerView;
    private RequestsAdapter adapter;
    private ArrayList<String> requestIDAcceptedSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myquest_acceptedquest, container, false);

        app = (RorpapApplication) getActivity().getApplicationContext();

        loading = new ProgressDialog(getActivity());
        loading.setTitle("Requests");
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        recyclerView = (RecyclerView) view.findViewById(R.id.acceptedquest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("recyclerView", recyclerView.toString());

        refresh();

        return view;
    }

    private String extractJSONInformation(JSONObject jo, String info){
        try {
            String information = jo.getString(info);
            return information;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void refresh() {
        loading.show();
        final String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);
        requestIDAcceptedSet = new ArrayList<String>();

        app.getHttpRequest().get("acceptance/getbymess/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);

                for (Request req : requestList) {
                    JSONObject jsonObject = req.getJsonObject();
                    String request_id = extractJSONInformation(jsonObject, "request_id");
                    if (!request_id.equals("")) {
                        if (!requestIDAcceptedSet.contains(request_id))
                            requestIDAcceptedSet.add(request_id);
                    }
                }

                app.getHttpRequest().get("request/get_request/Pending/!" + messenger_id, null, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Request> requestList = Request.getLists(response);
                        ArrayList<Request> acceptedRequestList = new ArrayList<Request>();

                        for (int i = 0; i < requestList.size(); i++) {
                            for (int j = 0; j < requestIDAcceptedSet.size(); j++) {
                                if (requestList.get(i).get_id().equals(requestIDAcceptedSet.get(j))) {
                                    acceptedRequestList.add(requestList.get(i));
                                }
                            }
                        }
                        Collections.sort(acceptedRequestList, new Comparator<Request>() {
                            @Override
                            public int compare(Request lhs, Request rhs) {
                                return rhs.get_id().compareTo(lhs.get_id());
                            }
                        });
                        RequestsAdapter adapter = new RequestsAdapter((RefreshableActivity) getActivity(), acceptedRequestList, RequestsAdapter.MY_REQUEST);
                        recyclerView.setAdapter(adapter);

                        if (loading.isShowing()) {
                            loading.dismiss();
                        }
                    }
                });
            }
        });
    }
}
