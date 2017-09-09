package com.syzible.loinniradminconsole.objects;

import android.content.Context;
import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ed on 08/09/2017.
 */

public class Locality {
    private String county, town;
    private int flag, countyCount, townCount;

    public Locality(Context context, JSONObject o) {
        try {
            this.county = o.getString("county");
            this.town = o.getString("locality");
            this.flag = getCountyFlag(context, county);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getCountyFileName(String county) {
        return county.toLowerCase().replace(" ", "_")
                .replace("á", "a").replace("é", "e")
                .replace("í", "i").replace("ó", "o")
                .replace("ú", "u");
    }

    private int getCountyFlag(Context context, String county) {
        if (county.equals("abroad"))
            this.county = "Éire";
        String countyFlagFile = getCountyFileName(this.county);
        return context.getResources().getIdentifier(countyFlagFile, "drawable", context.getPackageName());
    }

    public String getCounty() {
        return county;
    }

    public String getTown() {
        return town;
    }

    public int getFlag() {
        return flag;
    }

    public int getCountyCount() {
        return countyCount;
    }

    public int getTownCount() {
        return townCount;
    }
}
