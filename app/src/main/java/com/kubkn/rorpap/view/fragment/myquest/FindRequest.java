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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class FindRequest extends Fragment {

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myquest_findrequest, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.findrequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RorpapApplication app = (RorpapApplication) getActivity().getApplicationContext();
        final String sender_id = app.getPreferences().getString(Preferences.KEY_USERID);

        app.getHttpRequest().get("request/get_quest/Pending/!" + sender_id, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Request> requestList = Request.getLists(response);
                ArrayList<Request> resultList = new ArrayList<Request>();

                for(Request req : requestList){
                    if(!req.isHasAccept() && (!req.getSender_id().equals(sender_id))){
                        resultList.add(req);
                    }
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

        return view;
    }
}
