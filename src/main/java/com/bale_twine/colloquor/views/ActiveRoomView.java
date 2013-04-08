package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.Room;
import com.yammer.dropwizard.views.View;

import java.util.List;

public class ActiveRoomView extends BaseUserView {

    private final List<Room> rooms;

    public ActiveRoomView(String userName, List<Room> rooms) {
        super("activeRooms.mustache", userName);
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
