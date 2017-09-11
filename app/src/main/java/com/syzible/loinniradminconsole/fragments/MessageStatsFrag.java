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

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.RestClient;
import com.syzible.loinniradminconsole.objects.CardItem;
import com.syzible.loinniradminconsole.objects.Statistic;
import com.syzible.loinniradminconsole.views.CardViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ed on 08/09/2017.
 */

public class MessageStatsFrag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CardItem> messageStatistics = new ArrayList<>();
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
        progressDialog.setMessage("Loading message count data...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        RestClient.post(getActivity(),
                Endpoints.GET_MESSAGE_STATS,
                JSONUtils.getAuthPayload(getActivity()),
                new BaseJsonHttpResponseHandler<JSONObject>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONObject response) {
                        messageStatistics.clear();
                        System.out.println(response);

                        try {
                            for (int i = 0; i < response.names().length(); i++) {
                                String key = response.names().getString(i);
                                int value = response.getInt(response.names().getString(i));
                                String count = value + (value == 1 ? " message" : " messages");
                                messageStatistics.add(new Statistic(key, count));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.cancel();

                        adapter = new CardViewAdapter(messageStatistics);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONObject errorResponse) {
                        progressDialog.cancel();
                    }

                    @Override
                    protected JSONObject parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONObject(rawJsonData);
                    }
                });
    }
}
