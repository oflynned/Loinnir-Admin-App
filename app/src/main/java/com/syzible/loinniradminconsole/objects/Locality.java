package com.syzible.loinniradminconsole.objects;

import java.util.ArrayList;

/**
 * Created by ed on 08/09/2017.
 */

public class Locality {
    private String county;
    private ArrayList<String> towns;

    public Locality(String county, ArrayList<String> towns) {
        this.county = county;
        this.towns = towns;
    }

    public String getCounty() {
        return county;
    }

    public ArrayList<String> getTowns() {
        return towns;
    }
}
