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
import com.kubkn.rorpap.view.RefreshableFragment;
import com.kubkn.rorpap.view.RequestsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class FindRequest extends RefreshableFragment {

    private RorpapApplication app;

    private RecyclerView recyclerView;

    private HashSet<String> requestIDAcceptedSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myquest_findrequest, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.findrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        app = (RorpapApplication) getActivity().getApplicationContext();

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
        final String sender_id = app.getPreferences().getString(Preferences.KEY_USERID);

        requestIDAcceptedSet = new HashSet<>();

        app.getHttpRequest().get("acceptance/getbymess/" + sender_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);

                for (Request req : requestList) {
                    JSONObject jsonObject = req.getJsonObject();
                    String request_id = extractJSONInformation(jsonObject, "request_id");
                    Log.d("request_id", request_id);
                    if (!request_id.equals("")) {
                        requestIDAcceptedSet.add(request_id);
                    }
                }
            }
        });

        app.getHttpRequest().get("request/get_quest/Pending/!" + sender_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);
                ArrayList<Request> resultList = new ArrayList<Request>();

                for(Request req : requestList){
                    if((!req.getSender_id().equals(sender_id))){
                        resultList.add(req);
                    }
                }
                ArrayList<Request> listOfRequestToBeRemoved = new ArrayList<Request>();
                for(Request req : resultList){
                    Log.d("req.get_id()", req.get_id());
                    for(String reqID : requestIDAcceptedSet){
                        Log.d("reqID", reqID);
                        if(req.get_id().equals(reqID)){
                            listOfRequestToBeRemoved.add(req);
                        }
                    }
                }

                for(Request req : listOfRequestToBeRemoved){
                    resultList.remove(req);
                }

                Collections.sort(resultList, new Comparator<Request>() {
                    @Override
                    public int compare(Request lhs, Request rhs) {
                        return rhs.get_id().compareTo(lhs.get_id());
                    }
                });

                RequestsAdapter adapter = new RequestsAdapter(getActivity(), resultList, RequestsAdapter.MY_QUEST);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}