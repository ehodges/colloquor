package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.Room;
import com.yammer.dropwizard.views.View;

import java.util.List;

public class ActiveRoomView extends View {

    private final String userName;

    private final List<Room> rooms;

    public ActiveRoomView(String userName, List<Room> rooms) {
        super("activeRooms.mustache");
        this.userName = userName;
        this.rooms = rooms;
    }

    public String getUserName() {
        return userName;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
