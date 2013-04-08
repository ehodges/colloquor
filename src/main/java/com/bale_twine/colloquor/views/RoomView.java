package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

import java.util.UUID;

public class RoomView extends BaseUserView {
    private final UUID id;

    public RoomView(UUID id, String userName) {
        super("room.mustache", userName);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
