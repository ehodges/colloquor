package com.bale_twine.colloquor.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Room {

    @JsonProperty
    private final UUID id;

    @JsonProperty
    private String title;

    @JsonProperty
    private Set<User> occupants;

    public Room(UUID id) {
        this.id = id;
        this.title = "Room " + id.toString();
        this.occupants = new HashSet<User>();
    }

    public Room(UUID id, String title) {
        this(id);
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setOccupants(Set<User> occupants) {
        this.occupants.clear();
        this.occupants.addAll(occupants);
    }

    public Set<User> getOccupants() {
        return new HashSet<User>(occupants);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
