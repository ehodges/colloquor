package com.bale_twine.colloquor.core;

import com.bale_twine.colloquor.api.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room {

    private Set<User> occupants;

    private final UUID id;

    private String title = "";

    public Room(User initialUser) {
        occupants = new HashSet<User>();
        occupants.add(initialUser);

        id = UUID.randomUUID();
        this.title = "Room " + id.toString();
    }

    public Room(User initialUser, String title) {
        this(initialUser);
        this.title = title;
    }

    public Set<User> getOccupants() {
        return new HashSet<User>(occupants);
    }

    public void addUser(User user) {
        occupants.add(user);
    }

    public UUID getId() {
        return id;
    }

    public boolean removeUser(User user) {
        return occupants.remove(user);
    }

    public String getTitle() {
        return title;
    }
}
