package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.RestClient;
import com.syzible.loinniradminconsole.objects.PushNotification;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ed on 08/09/2017.
 */

public class OldPushNotificationsFrag extends Fragment {

    private View view;

    private RecyclerView recyclerView;
    private ArrayList<PushNotification> pushNotifications = new ArrayList<>();
    private PushNotificationsAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_recycle_view, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PushNotificationsAdapter();
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(getActivity());
        loadData();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.card_container_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return view;
    }

    private void loadData() {
        progressDialog.cancel();
        progressDialog.setMessage("Loading old push notifications...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.post(getActivity(),
                Endpoints.GET_ALL_PUSH_NOTIFICATIONS,
                JSONUtils.getAuthPayload(getActivity()),
                new BaseJsonHttpResponseHandler<JSONArray>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                        pushNotifications.clear();

                        if (response.length() == 0) {
                            pushNotifications.add(new PushNotification(
                                    "No Previous Push Notifications!",
                                    "Add one now by selecting new push notification under tools"));
                        } else {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    pushNotifications.add(new PushNotification(response.getJSONObject(i)));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        Collections.reverse(pushNotifications);

                        adapter = new PushNotificationsAdapter();
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressDialog.cancel();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                        progressDialog.cancel();
                    }

                    @Override
                    protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONArray(rawJsonData);
                    }
                });
    }

    class PushNotificationsAdapter extends RecyclerView.Adapter<PushNotificationsAdapter.CardViewHolder> {

        @Override
        public CardViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
            return new CardViewHolder(v);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            PushNotification notification = pushNotifications.get(position);
            holder.title.setText(notification.getTitle());
            holder.content.setText(notification.getContent());

            if (!notification.isEmptyPlaceholder()) {
                holder.broadcastTime.setText(getDate(notification.getTimeCreated()));
                holder.viewershipStats.setText("User count: " + notification.getUserCount() +
                        " / Users delivered to: " + notification.getUsersDeliveredTo());
            } else {
                holder.broadcastTime.setVisibility(View.GONE);
                holder.viewershipStats.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return pushNotifications.size();
        }

        public String getDate(long milliSeconds) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView title, content, broadcastTime, viewershipStats;

            CardViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.old_push_notification_card);
                title = (TextView) itemView.findViewById(R.id.card_title);
                content = (TextView) itemView.findViewById(R.id.card_content);
                broadcastTime = (TextView) itemView.findViewById(R.id.card_broadcast_time);
                viewershipStats = (TextView) itemView.findViewById(R.id.card_user_stats);
            }
        }

    }
}
