package com.syzible.loinniradminconsole.helpers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import com.syzible.loinniradminconsole.R;

/**
 * Created by ed on 08/09/2017.
 */

public class FragmentUtils {
    public static void setFragment(FragmentManager manager, Fragment fragment) {
        if (manager != null)
            manager.beginTransaction()
                    .replace(R.id.frame_holder, fragment)
                    .commit();
    }

    public static void setFragmentBackstack(FragmentManager manager, Fragment fragment) {
        if (manager != null)
            manager.beginTransaction()
                    .replace(R.id.frame_holder, fragment)
                    .addToBackStack(fragment.getClass().getName())
                    .commit();
    }

    public static void clearBackstack(FragmentManager manager) {
        if (manager != null)
            manager.popBackStack();
    }

    public static void removeFragment(FragmentManager manager) {
        if (manager != null)
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
