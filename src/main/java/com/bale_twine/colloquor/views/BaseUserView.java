package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

public abstract class BaseUserView extends View {

    private final String userName;

    protected BaseUserView(String templateName, String userName) {
        super(templateName);
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
