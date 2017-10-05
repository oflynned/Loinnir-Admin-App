package com.syzible.loinniradminconsole.objects;

import android.widget.TextView;

import com.syzible.loinniradminconsole.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 09/09/2017.
 */

public class PushNotification extends CardItem {

    private String url;
    private long timeCreated;
    private boolean isEmptyPlaceholder;

    public PushNotification(JSONObject o) throws JSONException{
        super(o.getString("title"), o.getString("content"), o.getString("link"),
                "UserData count: " + o.getInt("user_count_at_this_time") + " / " +
                "Users delivered to: " + o.getInt("user_count_delivered_to"));
        this.isEmptyPlaceholder = false;
        this.url = o.getString("link");
        this.timeCreated = o.getLong("broadcast_time");
    }

    public PushNotification(String title, String content) {
        super(title, content, R.drawable.ic_launcher_loinnir);
        this.isEmptyPlaceholder = true;
    }

    public String getUrl() {
        return url;
    }

    public boolean isEmptyPlaceholder() {
        return isEmptyPlaceholder;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
