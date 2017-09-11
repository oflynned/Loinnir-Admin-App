package com.syzible.loinniradminconsole.objects;

import android.content.Context;

import com.syzible.loinniradminconsole.helpers.LocalPrefs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 08/09/2017.
 */

public class Locality extends CardItem {

    public Locality(Context context, JSONObject o) throws JSONException {
        this(o.getString("county").equals("abroad") ? "Éire" : o.getString("county"),
                o.getString("locality").equals("abroad") ? "Abroad" : o.getString("locality"),
                o.getInt("locality_count") + (o.getInt("locality_count") == 1 ? " user " : " users ") + "in this locality",
                o.getInt("county_count") + (o.getInt("county_count") == 1 ? " user " : " users ") + "in this county",
                getCountyFlag(context, o.getString("county").equals("abroad") ? "Éire" : o.getString("county")));
    }

    private Locality(String county, String town, String townCount, String countyCount, int flag) {
        super(county, town, String.valueOf(townCount), String.valueOf(countyCount), flag);
    }

    private static String getCountyFileName(String county) {
        return county.toLowerCase().replace(" ", "_")
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u");
    }

    private static int getCountyFlag(Context context, String county) {
        String countyFlagFile = getCountyFileName(county);
        return context.getResources().getIdentifier(countyFlagFile, "drawable", context.getPackageName());
    }
}
