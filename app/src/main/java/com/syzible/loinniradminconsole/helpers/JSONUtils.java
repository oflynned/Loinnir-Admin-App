package com.syzible.loinniradminconsole.helpers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 07/09/2017.
 */

public class JSONUtils {

    public static JSONObject getAuthPayload(Context context) {
        return getAuthPayload(context, LocalPrefs.getUsername(context), LocalPrefs.getSecret(context));
    }

    public static JSONObject getAuthPayload(Context context, String username, String secret) {
        JSONObject o = new JSONObject();
        try {
            o.put("username", username);
            o.put("secret", secret);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }
}
