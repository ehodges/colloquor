package com.bale_twine.colloquor.views;


import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class RoomViewTest {

    @Test
    public void testGetRoomName() {
        final String roomName1 = "Test Room Name";
        RoomView roomView = new RoomView(UUID.randomUUID(), roomName1, "Fred");
        String roomName = roomView.getName();

        assertEquals(roomName1, roomName);
    }
}
