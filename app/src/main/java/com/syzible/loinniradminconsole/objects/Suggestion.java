package com.syzible.loinniradminconsole.objects;

import com.syzible.loinniradminconsole.helpers.CalendarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.syzible.loinniradminconsole.helpers.EncodingUtils.decodeText;

/**
 * Created by ed on 14/11/2017.
 */

public class Suggestion extends CardItem {
    private User user;
    private String suggestion, version;
    private long time;

    public Suggestion(JSONObject o) throws JSONException {
        this(o.getJSONObject("user").getString("forename"),
                o.getJSONObject("user").getString("surname"),
                o.getLong("time"),
                o.getString("version"),
                o.getString("suggestion"));

        this.user = new User(o.getJSONObject("user"));
    }

    private Suggestion(String forename, String surname, long time, String version, String suggestion) {
        super(decodeText(forename) + " " + decodeText(surname),
                CalendarUtils.getFormattedDate(time), version, suggestion);
        this.time = time;
        this.version = version;
        this.suggestion = suggestion;
    }

    public User getUser() {
        return user;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public long getTime() {
        return time;
    }

    public String getVersion() {
        return version;
    }
}
