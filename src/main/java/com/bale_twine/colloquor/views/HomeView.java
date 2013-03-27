package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

public class HomeView extends View {

    private final String userName;

    public HomeView(String userName) {
        super("home.mustache");
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}