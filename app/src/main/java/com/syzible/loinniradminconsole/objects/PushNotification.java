package com.syzible.loinniradminconsole.objects;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ed on 09/09/2017.
 */

public class PushNotification {

    private String title, content, url;
    private long timeCreated;
    private int userCount, usersDeliveredTo;
    private boolean isEmptyPlaceholder;

    public PushNotification(JSONObject o) {
        try {
            this.title = o.getString("title");
            this.content = o.getString("content");
            this.url = o.getString("link");
            this.timeCreated = o.getLong("broadcast_time");
            this.userCount = o.getInt("user_count_at_this_time");
            this.usersDeliveredTo = o.getInt("user_count_delivered_to");
            this.isEmptyPlaceholder = false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PushNotification(String title, String content) {
        this.title = title;
        this.content = content;
        this.isEmptyPlaceholder = true;
    }

    public boolean isEmptyPlaceholder() {
        return isEmptyPlaceholder;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public int getUserCount() {
        return userCount;
    }

    public int getUsersDeliveredTo() {
        return usersDeliveredTo;
    }
}
