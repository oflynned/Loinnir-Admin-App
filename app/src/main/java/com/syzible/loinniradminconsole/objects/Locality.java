package com.syzible.loinniradminconsole.objects;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 08/09/2017.
 */

public class Locality extends CardItem {

    public Locality(String county, String countyCount, int flag) {
        super(county, countyCount, flag);
    }

    public Locality(String county, String town, String townCount, String countyCount, int flag) {
        super(county, town, townCount, countyCount, flag);
    }

    private static String getCountyFileName(String county) {
        return county.toLowerCase().replace(" ", "_")
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u");
    }

    public static int getCountyFlag(Context context, String county) {
        String countyFlagFile = getCountyFileName(county);
        return context.getResources().getIdentifier(countyFlagFile, "drawable", context.getPackageName());
    }
}
