package com.bale_twine.colloquor.views;

public class TestView extends BaseUserView {

    private final String websocketConnectionString;

    public TestView(String userName, String websocketConnectionString) {
        super("test.mustache", userName);
        this.websocketConnectionString = websocketConnectionString;
    }

    public String getWebsocketConnectionString() {
        return websocketConnectionString;
    }
}
