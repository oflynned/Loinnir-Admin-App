package com.syzible.loinniradminconsole.objects;

/**
 * Created by ed on 10/09/2017.
 */

public class Statistic {
    private String key, value;

    public Statistic(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return formatTitle();
    }

    public String getValue() {
        return value;
    }

    private String formatTitle() {
        String[] words = key.split("_");
        String output = "";
        for (String word : words)
            output += String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1) + " ";

        return output;
    }
}
