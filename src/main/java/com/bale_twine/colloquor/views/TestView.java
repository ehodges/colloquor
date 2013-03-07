package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

public class TestView extends View {

    private final String websocketConnectionString;

    public TestView(String websocketConnectionString) {
        super("test.mustache");
        this.websocketConnectionString = websocketConnectionString;
    }

    public String getWebsocketConnectionString() {
        return websocketConnectionString;
    }
}
