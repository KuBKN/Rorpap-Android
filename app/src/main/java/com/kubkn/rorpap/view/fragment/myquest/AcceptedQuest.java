package com.kubkn.rorpap.view.fragment.myquest;

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
import com.kubkn.rorpap.view.RequestsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class AcceptedQuest extends Fragment {

    private RecyclerView recyclerView;
    private RequestsAdapter adapter;
    private HashSet<String> requestIDAcceptedSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myquest_acceptedquest, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final RorpapApplication app = (RorpapApplication) getActivity().getApplicationContext();
        String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);

        requestIDAcceptedSet = new HashSet<String>();

        app.getHttpRequest().get("acceptance/getbymess/" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);

                for (Request req : requestList) {
                    Log.d("REQUEST", req.toString());
                    JSONObject jsonObject = req.getJsonObject();
                    String request_id = extractJSONInformation(jsonObject, "request_id");
                    if(!request_id.equals("")){
                        requestIDAcceptedSet.add(request_id);
                    }
                }
            }
        });

        app.getHttpRequest().get("request/get_request/Pending/!" + messenger_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);
                ArrayList<Request> acceptedRequestList = new ArrayList<Request>();
                for(Request req : requestList){
                    JSONObject jsonObject = req.getJsonObject();
                    String request_id = extractJSONInformation(jsonObject, "_id");
                    for(String reqID : requestIDAcceptedSet){
                        if(request_id.equals(reqID)){
                            acceptedRequestList.add(req);
                        }
                    }
                }
                Collections.sort(acceptedRequestList, new Comparator<Request>() {
                    @Override
                    public int compare(Request lhs, Request rhs) {
                        return lhs.get_id().compareTo(rhs.get_id());
                    }
                });
                RequestsAdapter adapter = new RequestsAdapter(getActivity(), acceptedRequestList, RequestsAdapter.MY_QUEST);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

    public String extractJSONInformation(JSONObject jo, String info){
        try {
            String information = jo.getString(info);
            return information;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
