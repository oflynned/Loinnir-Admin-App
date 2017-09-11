package com.syzible.loinniradminconsole.objects;

/**
 * Created by ed on 10/09/2017.
 */

public class Statistic extends CardItem {

    public Statistic(String key, String value) {
        super(key, value);
    }

    @Override
    public String getTitle() {
        return formatTitle(super.getTitle());
    }

    private String formatTitle(String title) {
        String[] words = title.split("_");
        String output = "";
        for (String word : words)
            output += String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1) + " ";

        return output;
    }
}
