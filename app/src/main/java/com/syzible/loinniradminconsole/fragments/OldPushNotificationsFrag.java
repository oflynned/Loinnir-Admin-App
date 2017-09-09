package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.RestClient;
import com.syzible.loinniradminconsole.objects.PushNotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ed on 08/09/2017.
 */

public class OldPushNotificationsFrag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<PushNotification> pushNotifications = new ArrayList<>();
    private PushNotificationsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.old_push_notifications_frag, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.old_push_notifications_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PushNotificationsAdapter();
        recyclerView.setAdapter(adapter);

        RestClient.post(getActivity(),
                Endpoints.GET_ALL_PUSH_NOTIFICATIONS,
                JSONUtils.getAuthPayload(getActivity()),
                new BaseJsonHttpResponseHandler<JSONArray>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                        System.out.println(response);
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

                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {

                    }

                    @Override
                    protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONArray(rawJsonData);
                    }
                });
        return view;
    }

    class PushNotificationsAdapter extends RecyclerView.Adapter<PushNotificationsAdapter.CardViewHolder> {

        @Override
        public CardViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.push_notification_card, parent, false);
            return new CardViewHolder(v);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            holder.title.setText(pushNotifications.get(position).getTitle());
            holder.content.setText(pushNotifications.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return pushNotifications.size();
        }

        class CardViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView title, content;

            CardViewHolder(View itemView) {
                super(itemView);
                cardView = (CardView) itemView.findViewById(R.id.old_push_notification_card);
                title = (TextView) itemView.findViewById(R.id.card_title);
                content = (TextView) itemView.findViewById(R.id.card_content);
            }
        }

    }
}
