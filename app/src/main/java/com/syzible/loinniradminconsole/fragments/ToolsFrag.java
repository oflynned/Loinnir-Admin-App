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

        view.findViewById(R.id.button_new_push_notification).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new NewPushNotificationFrag()));

        view.findViewById(R.id.button_past_push_notifications).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new OldPushNotificationsFrag()));

        view.findViewById(R.id.button_view_localities).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new ViewLocalitiesFrag()));

        view.findViewById(R.id.button_view_counties).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new ViewCountiesFrag()));

        view.findViewById(R.id.button_view_user_stats).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new UserStatsFrag()));

        view.findViewById(R.id.button_view_message_stats).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new MessageStatsFrag()));

        view.findViewById(R.id.button_user_list).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new UserListFrag()));

        view.findViewById(R.id.button_app_suggestions).setOnClickListener(
                v -> FragmentUtils.setFragmentBackstack(getFragmentManager(), new SuggestionsFrag()));

        return view;
    }
}
