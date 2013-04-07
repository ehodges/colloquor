package com.bale_twine.colloquor.core;

import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.*;

public class ActiveRooms {
    private final Set<Room> activeRooms;

    public ActiveRooms() {
        activeRooms = new ConcurrentHashSet<Room>();
    }

    public void add(Room room) {
        activeRooms.add(room);
    }

    public int size() {
        return activeRooms.size();
    }

    public boolean contains(Room room) {
        return activeRooms.contains(room);
    }

    // TODO : This needs to be more efficient.
    public Room getRoom(UUID uuid) {
        Iterator<Room> iterator = activeRooms.iterator();

        while(iterator.hasNext()) {
            Room currentRoom = iterator.next();
            if(currentRoom.getId().equals(uuid)) {
                return currentRoom;
            }
        }
        return null;
    }

    public Set<Room> getRooms() {
        Set<Room> rooms = new HashSet<Room>(activeRooms);
        return rooms;
    }

    public void removeAllRooms() {
        activeRooms.clear();
    }
}
