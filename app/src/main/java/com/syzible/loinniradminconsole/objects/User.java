package com.syzible.loinniradminconsole.objects;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 10/09/2017.
 */

public class User extends CardItem {

    public User(JSONObject o, Bitmap b) throws JSONException{
        // TODO
        super(null, null, null);
    }

    public User(String title, String content, int icon) {
        super(title, content, icon);
    }
}
