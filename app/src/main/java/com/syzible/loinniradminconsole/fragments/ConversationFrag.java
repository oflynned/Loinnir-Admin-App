package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.BitmapUtils;
import com.syzible.loinniradminconsole.helpers.CachingUtil;
import com.syzible.loinniradminconsole.helpers.EncodingUtils;
import com.syzible.loinniradminconsole.helpers.JSONUtils;
import com.syzible.loinniradminconsole.networking.Endpoints;
import com.syzible.loinniradminconsole.networking.GetImage;
import com.syzible.loinniradminconsole.networking.NetworkCallback;
import com.syzible.loinniradminconsole.networking.RestClient;
import com.syzible.loinniradminconsole.objects.Message;
import com.syzible.loinniradminconsole.objects.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ed on 08/09/2017.
 */

public class ConversationFrag extends Fragment {
    private View view;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Message> paginatedMessages = new ArrayList<>();

    private MessagesListAdapter<Message> adapter;

    private ProgressBar progressBar;
    private Spinner spinner;
    private ArrayList<String> idPairs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.conversations_frag, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.conversations_progress_bar);
        spinner = (Spinner) view.findViewById(R.id.name_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadMessages(getSpinnerItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        loadAreaNames();
    }

    private void setSpinnerData() {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, idPairs);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    private String[] getSpinnerItem(int position) {
        return idPairs.get(position).split(" ");
    }

    private void loadAreaNames() {
        RestClient.post(getActivity(),
                Endpoints.GET_PARTNER_ID_PAIRS,
                JSONUtils.getAuthPayload(getActivity()),
                new BaseJsonHttpResponseHandler<JSONArray>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String pair = "";
                                for (int j = 0; j < response.getJSONArray(i).length(); j++) {
                                    pair += response.getJSONArray(i).getString(j) + " ";
                                }
                                pair = pair.trim();
                                idPairs.add(pair);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        setSpinnerData();

                        loadMessages(idPairs.get(0).split(" "));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {

                    }

                    @Override
                    protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONArray(rawJsonData);
                    }
                });
    }

    private void setupAdapter(View view) {
        MessagesList messagesList = (MessagesList) view.findViewById(R.id.messages_list);
        adapter = new MessagesListAdapter<>("-1", loadImage());
        messagesList.setAdapter(adapter);
    }

    private void loadMessages(String[] pair) {
        setupAdapter(view);
        RestClient.post(getActivity(),
                Endpoints.GET_PARTNER_ID_PAIR_CONVERSATION,
                JSONUtils.getAuthConversationPayload(getActivity(), pair),
                new BaseJsonHttpResponseHandler<JSONArray>() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, JSONArray response) {
                        messages.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject userMessage = response.getJSONObject(i);
                                String id = userMessage.getJSONObject("_id").getString("$oid");
                                String messageContent = EncodingUtils.decodeText(userMessage.getString("message"));
                                long timeSent = userMessage.getLong("time");

                                User user = new User(userMessage.getJSONObject("user"));
                                Message message = new Message(id, user, timeSent, messageContent);
                                messages.add(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.reverse(messages);
                        adapter.addToEnd(messages, true);
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, JSONArray errorResponse) {
                        System.out.println("failed?");
                    }

                    @Override
                    protected JSONArray parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                        return new JSONArray(rawJsonData);
                    }
                });
    }

    private ImageLoader loadImage() {
        return new ImageLoader() {
            @Override
            public void loadImage(final ImageView imageView, final String url) {
                final String fileName = url.split("/")[3];

                if (!CachingUtil.doesImageExist(getActivity(), fileName)) {
                    new GetImage(new NetworkCallback<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            Bitmap croppedImage = BitmapUtils.getCroppedCircle(response);
                            Bitmap scaledAvatar = BitmapUtils.scaleBitmap(croppedImage, BitmapUtils.BITMAP_SIZE_SMALL);
                            imageView.setImageBitmap(scaledAvatar);
                            CachingUtil.cacheImage(getActivity(), fileName, scaledAvatar);
                        }

                        @Override
                        public void onFailure() {
                            System.out.println("dl failure on chat pic");
                        }
                    }, url).execute();
                } else {
                    Bitmap cachedImage = CachingUtil.getCachedImage(getActivity(), fileName);
                    imageView.setImageBitmap(cachedImage);
                }
            }
        };
    }
}
