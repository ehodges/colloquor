package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.Room;

import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

public class RoomView extends BaseUserView {
    private final UUID id;

    private final String name;
    private final String websocketEndpoint;
    private Set<User> occupants;

    public RoomView(Room room, String userName, String websocketEndpoint) {
        super("room.mustache", userName);
        this.id = room.getId();
        this.name = room.getTitle();
        this.occupants = room.getOccupants();
        this.websocketEndpoint = websocketEndpoint;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public Set<User> getOccupants() {
        return occupants;
    }

    public String getWebsocketEndpoint() {
        return websocketEndpoint;
    }
}
