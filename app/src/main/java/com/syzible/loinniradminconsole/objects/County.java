package com.syzible.loinniradminconsole.objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 17/09/2017.
 */

public class County extends Locality {
    public County(Context context, JSONObject o) throws JSONException {
        super(o.getString("county").equals("abroad") ? "Éire" : o.getString("county"),
                o.getInt("county_count") + (o.getInt("county_count") == 1 ? " user " : " users ") + "in this county",
                getCountyFlag(context, o.getString("county").equals("abroad") ? "Éire" : o.getString("county")));
    }
}
