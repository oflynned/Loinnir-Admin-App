package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syzible.loinniradminconsole.R;

/**
 * Created by ed on 08/09/2017.
 */

public class StatisticsFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics_frag, container, false);
    }
}
