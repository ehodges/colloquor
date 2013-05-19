package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.Room;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

public class RoomView extends BaseUserView {
    private final UUID id;

    private final String name;
    private Set<User> occupants;

    public RoomView(UUID id, String roomName, String userName) {
        super("room.mustache", userName);
        this.id = id;
        this.name = roomName;
        this.occupants = new TreeSet<User>();
    }

    public RoomView(Room room, String userName) {
        super("room.mustache", userName);
        this.id = room.getId();
        this.name = room.getTitle();
        this.occupants = room.getOccupants();
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
}
