package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.api.Room;
import com.bale_twine.colloquor.views.RoomView;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomResourceTest {

    public static final String TEST_NAME = "Fred";

    @Test
    public void testGetExistingRoom() {
        RoomResource roomResource = new RoomResource();

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        com.bale_twine.colloquor.core.Room newRoom = new com.bale_twine.colloquor.core.Room(new User(TEST_NAME));
        UUID uuid = newRoom.getId();
        activeRooms.add(newRoom);
        Room retrievedRoom = roomResource.getRoom(uuid.toString());

        assertEquals(TEST_NAME, retrievedRoom.getOccupants().iterator().next().getName());
    }

    @Test
    public void testGetExistingRoomView() {
        RoomResource roomResource = new RoomResource();

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        com.bale_twine.colloquor.core.Room newRoom = mock(com.bale_twine.colloquor.core.Room.class);

        UUID uuid = UUID.randomUUID();
        when(newRoom.getId()).thenReturn(uuid);

        activeRooms.add(newRoom);
        RoomView roomView = roomResource.getRoomView(uuid.toString());
        assertEquals(uuid, roomView.getId());
    }
}
