package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.core.Room;

import java.util.UUID;

public class RoomView extends BaseUserView {
    private final UUID id;

    private final String name;

    public RoomView(UUID id, String roomName, String userName) {
        super("room.mustache", userName);
        this.id = id;
        this.name = roomName;
    }

    public RoomView(Room room, String userName) {
        super("room.mustache", userName);
        this.id = room.getId();
        this.name = room.getTitle();
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }
}
