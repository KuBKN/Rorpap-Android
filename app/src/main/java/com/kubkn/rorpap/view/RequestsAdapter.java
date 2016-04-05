package com.kubkn.rorpap.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;

import java.util.ArrayList;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    public static final byte UNKNOWN = 0;
    public static final byte MY_REQUEST_PENDING = 1;
    public static final byte MY_QUEST_PENDING = 2;
    public static final byte MY_REQUEST_RESERVED= 3;
    public static final byte MY_QUEST_RESERVED = 4;

    private ArrayList<Request> requests;
    private byte cardType;


    public RequestsAdapter(ArrayList<Request> requests, byte cardType) {
        this.requests = requests;
        this.cardType = cardType;
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
        holder.textViewSender.setText(requests.get(position).getSender_id());
        holder.textViewComment.setText(requests.get(position).getComment());

        if (cardType == UNKNOWN) {
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
        }

        if (cardType == MY_REQUEST_PENDING) {
            holder.buttonGroupMyRequestPending.setVisibility(View.VISIBLE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
        }

        if (cardType == MY_QUEST_PENDING) {
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.VISIBLE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
        }

        if (cardType == MY_REQUEST_RESERVED) {
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.VISIBLE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
        }

        if (cardType == MY_QUEST_RESERVED) {
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.VISIBLE);
        }
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
        public TextView textViewSender;
        public TextView textViewComment;

        public LinearLayout linearLayoutSender;

        public LinearLayout buttonGroupMyRequestPending;
        public LinearLayout buttonGroupMyQuestPending;
        public LinearLayout buttonGroupMyRequestReserved;
        public LinearLayout buttonGroupMyQuestReserved;

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
            textViewSender = (TextView) view.findViewById(R.id.textViewSender);
            textViewComment = (TextView) view.findViewById(R.id.textViewComment);

            linearLayoutSender = (LinearLayout) view.findViewById(R.id.linearLayoutSender);

            buttonGroupMyRequestPending = (LinearLayout) view.findViewById(R.id.buttonGroupMyRequestPending);
            buttonGroupMyQuestPending = (LinearLayout) view.findViewById(R.id.buttonGroupMyQuestPending);
            buttonGroupMyRequestReserved = (LinearLayout) view.findViewById(R.id.buttonGroupMyRequestReserved);
            buttonGroupMyQuestReserved = (LinearLayout) view.findViewById(R.id.buttonGroupMyQuestReserved);
        }
    }
}
