package com.kubkn.rorpap.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.kubkn.rorpap.R;
import com.kubkn.rorpap.model.Request;
import com.kubkn.rorpap.service.Preferences;
import com.kubkn.rorpap.service.RorpapApplication;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by batmaster on 4/5/16 AD.
 */
public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.ViewHolder> {

    public static final byte UNKNOWN = 0;
    public static final byte MY_REQUEST = 1;
    public static final byte MY_QUEST = 2;

    private Activity activity;
    private ArrayList<Request> requests;
    private byte cardType;

    RorpapApplication app;

    public RequestsAdapter(Activity activity, ArrayList<Request> requests, byte cardType) {
        this.activity = activity;
        this.requests = requests;
        this.cardType = cardType;

        app = (RorpapApplication) activity.getApplicationContext();
    }

    public void addRequests(ArrayList<Request> requests) {
        this.requests.addAll(requests);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_request, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
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

        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getHttpRequest().post("request/remove/", null, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("my response tag", "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        Log.d("my error response tag", "onErrorResponse: " + error);
                    }
                });
            }
        });

        holder.buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);
                app.getHttpRequest().post("acceptance/add/" + messenger_id + "/" + requests.get(position).get_id(), null, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d("my response tag", "onResponse: " + response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        Log.d("my error response tag", "onErrorResponse: " + error);
                    }
                });
            }
        });

        holder.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_token);

                EditText editTextToken = (EditText) dialog.findViewById(R.id.editTextToken);

                Button buttonSubmit = (Button) dialog.findViewById(R.id.buttonSubmit);
                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String messenger_id = app.getPreferences().getString(Preferences.KEY_USERID);
                        // TODO Check token
                        if (true) {
                            app.getHttpRequest().post("request/accept/" + messenger_id + "/" + requests.get(position).get_id(), null, new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    // start sending gps
                                    HashMap<String, String> params = new HashMap<String, String>();
                                    params.put("type", "0");
                                    params.put("signal", "101");
                                    params.put("title", "");
                                    params.put("content", "");
                                    params.put("user_id", app.getPreferences().getString(Preferences.KEY_USERID));
                                    app.getHttpRequest().post("gcm/push/", params, new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(activity.getApplicationContext(), "Start tracking commanded...", Toast.LENGTH_SHORT).show();
                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                dialog.show();
            }
        });

        holder.buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_token);

                EditText editTextToken = (EditText) dialog.findViewById(R.id.editTextToken);
                Button buttonSubmit = (Button) dialog.findViewById(R.id.buttonSubmit);

                buttonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO Check token
                        if (true) {
                            HashMap<String, String> params = new HashMap<String, String>();
                            params.put("_id", requests.get(position).get_id());
                            app.getHttpRequest().post("request/finish/", params, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    app.getHttpRequest().get("request/get_quest/Inprogress/" + app.getPreferences().getString(Preferences.KEY_USERID), null, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            ArrayList<Request> requests = Request.getLists(response);
                                            if (requests.size() > 0) {
                                                Toast.makeText(activity.getApplicationContext(), "Another quest(s) left, still not stop tracking.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                HashMap<String, String> params = new HashMap<String, String>();
                                                params.put("type", "0");
                                                params.put("signal", "102");
                                                params.put("title", "");
                                                params.put("content", "");
                                                params.put("user_id", app.getPreferences().getString(Preferences.KEY_USERID));
                                                app.getHttpRequest().post("gcm/push/", params, new Response.Listener<String>() {

                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(activity.getApplicationContext(), "Stop tracking commanded...", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });
                                    Log.d("my response tag", "onResponse: " + response);
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.d("my error response tag", "onErrorResponse: " + error);
                                }
                            });
                        }
                    }
                });

                dialog.show();
            }
        });

        holder.buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = app.getApplicationContext();
                Intent intent = new Intent(context, MapActivity.class);
                ArrayList<String> requestLocations = new ArrayList<String>();
                requestLocations.add(requests.get(position).getFromLoc());
                requestLocations.add(requests.get(position).getToLoc());
                intent.putStringArrayListExtra("Request locations", requestLocations);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        if (cardType == UNKNOWN) {
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.GONE);
            holder.buttonGroupShowMap.setVisibility(View.GONE);
        } else if (cardType == MY_REQUEST && requests.get(position).getType().equals("Pending")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF4081"));
            holder.buttonGroupMyRequestPending.setVisibility(View.VISIBLE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.VISIBLE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        } else if (cardType == MY_QUEST && requests.get(position).getType().equals("Pending")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FF4081"));
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.VISIBLE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.GONE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        } else if (cardType == MY_REQUEST && requests.get(position).getType().equals("Reserved")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFE082"));
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.VISIBLE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.VISIBLE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        } else if (cardType == MY_QUEST && requests.get(position).getType().equals("Reserved")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#FFE082"));
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.VISIBLE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.VISIBLE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        }
        else if (cardType == MY_QUEST && requests.get(position).getType().equals("Inprogress")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#42A5F5"));
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.VISIBLE);
            holder.buttonGroupCancel.setVisibility(View.VISIBLE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        }
        else if (cardType == MY_QUEST && requests.get(position).getType().equals("Finished")) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#00E676"));
            holder.buttonGroupMyRequestPending.setVisibility(View.GONE);
            holder.buttonGroupMyQuestPending.setVisibility(View.GONE);
            holder.buttonGroupMyRequestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestReserved.setVisibility(View.GONE);
            holder.buttonGroupMyQuestInprogress.setVisibility(View.GONE);
            holder.buttonGroupCancel.setVisibility(View.GONE);
            holder.buttonGroupShowMap.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return requests.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
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
        public LinearLayout buttonGroupMyQuestInprogress;
        public LinearLayout buttonGroupCancel;
        public LinearLayout buttonGroupShowMap;

        public Button buttonEdit;
        public Button buttonRemove;
        public Button buttonAccept;
        public Button buttonSend;
        public Button buttonStart;
        public Button buttonFinish;
        public Button buttonMap;
        public Button buttonCancel;

        public ViewHolder(View view) {
            super(view);

            cardView = (CardView) view.findViewById(R.id.cardView);

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
            buttonGroupMyQuestInprogress = (LinearLayout) view.findViewById(R.id.buttonGroupMyQuestInprogress);
            buttonGroupCancel = (LinearLayout) view.findViewById(R.id.buttonGroupCancel);
            buttonGroupShowMap = (LinearLayout) view.findViewById(R.id.buttonGroupShowMap);

            buttonEdit = (Button) view.findViewById(R.id.buttonEdit);
            buttonRemove = (Button) view.findViewById(R.id.buttonRemove);
            buttonAccept = (Button) view.findViewById(R.id.buttonAccept);
            buttonSend = (Button) view.findViewById(R.id.buttonSend);
            buttonStart = (Button) view.findViewById(R.id.buttonStart);
            buttonFinish = (Button) view.findViewById(R.id.buttonFinish);
            buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
            buttonMap = (Button) view.findViewById(R.id.buttonMap);

        }
    }
}
