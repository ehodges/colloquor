package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

import java.util.List;

public class HomeView extends View {

    private final List<String> dbNames;

    public HomeView(List<String> dbNames) {
        super("home.mustache");
        this.dbNames = dbNames;
    }

    public String getNames() {
        StringBuffer sb = new StringBuffer();

        for (String name : dbNames) {
            sb.append(name);
            sb.append(", ");
        }
        return sb.toString();
    }
}