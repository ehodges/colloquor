package com.bale_twine.colloquor.views;

import com.yammer.dropwizard.views.View;

import java.util.UUID;

public class RoomView extends View {
    private UUID id;

    public RoomView(UUID id) {
        super("room.mustache");
        this.id = id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
