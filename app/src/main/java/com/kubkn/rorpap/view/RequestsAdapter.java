package com.kubkn.rorpap.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;

import java.util.ArrayList;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

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
