package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.helpers.FragmentUtils;

/**
 * Created by ed on 08/09/2017.
 */

public class ToolsFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_frag, container, false);

        Button newPushNotificationButton = (Button) view.findViewById(R.id.button_new_push_notification);
        newPushNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragmentBackstack(getFragmentManager(), new NewPushNotificationFrag());
            }
        });

        Button oldPushNotificationsButton = (Button) view.findViewById(R.id.button_past_push_notifications);
        oldPushNotificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragmentBackstack(getFragmentManager(), new OldPushNotificationsFrag());
            }
        });

        Button showLocalitiesFragment = (Button) view.findViewById(R.id.button_view_localities);
        showLocalitiesFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragmentBackstack(getFragmentManager(), new ViewLocalitiesFrag());
            }
        });

        Button showCountiesFragment = (Button) view.findViewById(R.id.button_view_counties);
        showCountiesFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.setFragmentBackstack(getFragmentManager(), new ViewCountiesFrag());
            }
        });

        Button deleteUserButton = (Button) view.findViewById(R.id.button_delete_user);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button deleteLocalityChat = (Button) view.findViewById(R.id.button_locality_chat);
        deleteLocalityChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
