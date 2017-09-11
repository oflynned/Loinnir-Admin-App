package com.syzible.loinniradminconsole.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.objects.CardItem;

import java.util.ArrayList;

/**
 * Created by ed on 11/09/2017.
 */

public abstract class CardsFragment extends Fragment {

    private ArrayList<CardItem> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_recycle_view, container, false);
    }
}
