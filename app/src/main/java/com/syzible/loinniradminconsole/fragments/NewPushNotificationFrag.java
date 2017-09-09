package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.networking.Endpoints;

/**
 * Created by ed on 08/09/2017.
 */

public class NewPushNotificationFrag extends Fragment {

    private View view;
    private Button pnDispatchButton;
    private EditText pnTitleEditText, pnContentEditText, pnUrlEditText;
    private TextView pnPreviewCardTitle, pnPreviewCardContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.new_push_notification_frag, container, false);

        pnTitleEditText = (EditText) view.findViewById(R.id.new_push_notification_et_title);
        pnContentEditText = (EditText) view.findViewById(R.id.new_push_notification_et_content);
        pnUrlEditText = (EditText) view.findViewById(R.id.new_push_notification_et_link);

        pnPreviewCardTitle = (TextView) view.findViewById(R.id.push_notification_preview_card_title);
        pnPreviewCardContent = (TextView) view.findViewById(R.id.push_notification_preview_card_content);

        pnTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String title = getTitleField().length() == 0 ? "Push Notification Title" : getTitleField();
                pnPreviewCardTitle.setText(title);
            }
        });

        pnContentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = getContentField().length() == 0 ? "Push Notification Content" : getContentField();
                pnPreviewCardContent.setText(content);
            }
        });

        pnDispatchButton = (Button) view.findViewById(R.id.button_dispatch_new_push_notification);
        pnDispatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!areFieldsAcceptable()) {
                    Toast.makeText(getActivity(), "Fields unacceptable, make sure title and content have text", Toast.LENGTH_SHORT).show();
                } else {
                    pnTitleEditText.setText(null);
                    pnContentEditText.setText(null);
                    pnUrlEditText.setText(null);
                    Toast.makeText(getActivity(), "Sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private String getTitleField() {
        return pnTitleEditText.getText().toString().trim();
    }

    private String getContentField() {
        return pnContentEditText.getText().toString().trim();
    }

    private String getUrlField() {
        String pnUrl = pnUrlEditText.getText().toString().trim();
        return pnUrl.length() == 0 ? "https://www.facebook.com/LoinnirApp" : pnUrl;
    }

    private boolean areFieldsAcceptable() {
        boolean isTitleAcceptable = getTitleField().length() > 0;
        boolean isContentAcceptable = getContentField().length() > 0;
        boolean isUrlAcceptable = URLUtil.isValidUrl(getUrlField());
        return isTitleAcceptable && isContentAcceptable && isUrlAcceptable;
    }
}
