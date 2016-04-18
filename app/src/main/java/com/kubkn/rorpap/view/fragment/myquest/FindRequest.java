package com.kubkn.rorpap.view.fragment.myquest;

import android.app.ProgressDialog;
import android.os.Bundle;
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
    private ProgressDialog loading;

    private RecyclerView recyclerView;

    private HashSet<String> requestIDAcceptedSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myquest_findrequest, container, false);

        app = (RorpapApplication) getActivity().getApplicationContext();

        loading = new ProgressDialog(getActivity());
        loading.setTitle("Requests");
        loading.setMessage("Loading...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        recyclerView = (RecyclerView) view.findViewById(R.id.findrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ((RefreshableActivity) getActivity()).setSwipeRefreshEnable(lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0);
            }
        });

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
        recyclerView.setAdapter(null);
        final String sender_id = app.getPreferences().getString(Preferences.KEY_USERID);

        requestIDAcceptedSet = new HashSet<>();

        app.getHttpRequest().get("acceptance/getbymess/" + sender_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);

                for (Request req : requestList) {
                    JSONObject jsonObject = req.getJsonObject();
                    String request_id = extractJSONInformation(jsonObject, "request_id");

                    if (!request_id.equals("")) {
                        requestIDAcceptedSet.add(request_id);
                    }
                }

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
                            for(String reqID : requestIDAcceptedSet){
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

                        RequestsAdapter adapter = new RequestsAdapter((RefreshableActivity) getActivity(), resultList, RequestsAdapter.MY_QUEST);
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