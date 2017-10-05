package com.syzible.loinniradminconsole.objects;

import android.content.Context;

import com.syzible.loinniradminconsole.helpers.CalendarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.syzible.loinniradminconsole.helpers.EncodingUtils.decodeText;
import static com.syzible.loinniradminconsole.objects.Locality.getCountyFlag;

/**
 * Created by ed on 10/09/2017.
 */

public class UserData extends CardItem {

    public UserData(Context context, JSONObject o) throws JSONException {
        this(o.getString("forename"),
                o.getString("surname"),
                (o.getString("locality").equals("abroad") ? "Abroad" : o.getString("locality")) + ", " + (o.getString("county").equals("abroad") ? "Éire" : o.getString("county")),
                "Total Messages: " + o.getInt("total_message_count") +
                        "\nLocality Messages: " + o.getInt("total_locality_message_count") +
                        "\nPartner Messages: " + o.getInt("total_partner_message_count") +
                        "\nPartner Count: " + o.getJSONArray("partners").length(),
                (o.has("last_active") ? "Last Active: " + CalendarUtils.getFormattedDate(o.getLong("last_active")) : "") +
                        "\nMember Since: " + CalendarUtils.getFormattedDate(o.getLong("time_created")),
                getCountyFlag(context, o.getString("county")));
    }

    private UserData(String forename, String surname, String location, String countStats, String timeStats, int icon) {
        this(decodeText(forename) + " " + decodeText(surname), location, countStats, timeStats, icon);
    }

    private UserData(String name, String location, String counts, String timeStats, int icon) {
        super(name, location, counts, timeStats, icon);
    }
}
