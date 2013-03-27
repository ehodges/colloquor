package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

public class TestView extends View {

    private final String userName;
    private final String websocketConnectionString;

    public TestView(String userName, String websocketConnectionString) {
        super("test.mustache");
        this.userName = userName;
        this.websocketConnectionString = websocketConnectionString;
    }

    public String getUserName() {
        return userName;
    }

    public String getWebsocketConnectionString() {
        return websocketConnectionString;
    }
}
