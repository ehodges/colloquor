package com.bale_twine.colloquor.api;

import java.util.HashSet;
import java.util.Set;

public class Room {
    private Set<User> occupants;

    public Room(User initialUser) {
        occupants = new HashSet<User>();
        occupants.add(initialUser);


    }

    public Set<User> getOccupants() {
        Set<User> returnList = new HashSet<User>(occupants);
        return returnList;
    }

    public void addUser(User user) {
        occupants.add(user);
    }
}
