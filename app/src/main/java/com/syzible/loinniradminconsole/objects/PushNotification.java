package com.syzible.loinniradminconsole.objects;

import android.widget.TextView;

/**
 * Created by ed on 09/09/2017.
 */

public class PushNotification {

    private String title, content, url;

    public PushNotification(String title, String content, String url) {
        this.title = title;
        this.content = content;
        this.url = url;
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
}
