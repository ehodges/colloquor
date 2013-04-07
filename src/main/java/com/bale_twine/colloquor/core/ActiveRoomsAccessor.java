package com.bale_twine.colloquor.core;

import com.bale_twine.colloquor.core.ActiveRooms;

public class ActiveRoomsAccessor {

    private static final ActiveRooms activeRooms = new ActiveRooms();

    public static ActiveRooms getActiveRooms() {
        return activeRooms;
    }
}
