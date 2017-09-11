package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.helpers.LocalPrefs;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.RestClient;
import com.syzible.loinniradminconsole.objects.CardItem;
import com.syzible.loinniradminconsole.objects.PushNotification;
import com.syzible.loinniradminconsole.views.Callback;
import com.syzible.loinniradminconsole.views.CardViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ed on 08/09/2017.
 */

public class OldPushNotificationsFrag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CardItem> pushNotifications = new ArrayList<>();
    private CardViewAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_recycle_view, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.card_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardViewAdapter();
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

                        adapter = new CardViewAdapter(pushNotifications, new Callback() {
                            @Override
                            public void onCallback(int position) {
                                PushNotification p = (PushNotification) pushNotifications.get(position);
                                dispatchPushNotification(p);
                            }
                        });
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

    private void dispatchPushNotification(PushNotification notification) {
        JSONObject payload = new JSONObject();
        try {
            payload.put("username", LocalPrefs.getUsername(getActivity()));
            payload.put("secret", LocalPrefs.getSecret(getActivity()));
            payload.put("push_notification_title", notification.getTitle());
            payload.put("push_notification_content", notification.getContent());
            payload.put("push_notification_link", notification.getUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.cancel();
        progressDialog.setMessage("Broadcasting push notification...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        RestClient.post(getActivity(),
                Endpoints.BROADCAST_PUSH_NOTIFICATION,
                payload,
                new BaseJsonHttpResponseHandler<JSONObject>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                        Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {
                        Toast.makeText(getActivity(), "Error on broadcasting push notification", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();
                    }

                    @Override
                    protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONObject(rawJsonData);
                    }
                });
    }
}
