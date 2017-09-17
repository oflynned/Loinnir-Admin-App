package com.syzible.loinniradminconsole.objects;

import android.graphics.Bitmap;

import com.syzible.loinniradminconsole.R;

/**
 * Created by ed on 11/09/2017.
 */

public abstract class CardItem {

    private String title, content, subContent, subsubContent;
    private int icon;

    public CardItem(String title, String content) {
        this(title, content, null, null, 0);
    }

    public CardItem(String title, String content, int icon) {
        this(title, content, null, null, icon);
    }

    public CardItem(String title, String content, String subContent) {
        this(title, content, subContent, null, 0);
    }

    public CardItem(String title, String content, String subContent, String subsubContent) {
        this(title, content, subContent, subsubContent, 0);
    }

    public CardItem(String title, String content, String subContent, String subsubContent, int icon) {
        this.title = title;
        this.content = content;
        this.subContent = subContent;
        this.subsubContent = subsubContent;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSubContent() {
        return subContent;
    }

    public String getSubsubContent() {
        return subsubContent;
    }

    public boolean isIconBlank() {
        return icon == 0;
    }

    public boolean shouldDrawBackground() {
        return icon != 0 && icon != R.drawable.ic_launcher_loinnir;
    }

    public int getIcon() {
        return icon;
    }
}
