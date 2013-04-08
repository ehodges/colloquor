package com.bale_twine.colloquor.views;

public class HomeView extends BaseUserView {

    public HomeView(String userName) {
        super("home.mustache", userName);
    }
}
