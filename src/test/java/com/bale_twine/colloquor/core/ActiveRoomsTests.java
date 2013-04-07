package com.bale_twine.colloquor.core;

import org.junit.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActiveRoomsTests {

    @Test
    public void testAddSingleRoom() {
        Room mockRoom = mock(Room.class);

        ActiveRooms activeRooms = new ActiveRooms();
        activeRooms.add(mockRoom);
        assertEquals(1, activeRooms.size());
        assertTrue(activeRooms.contains(mockRoom));
    }

    @Test
    public void testAddMultipleRooms() {
        Room mockRoom = mock(Room.class);
        Room mockRoomOne = mock(Room.class);

        ActiveRooms activeRooms = new ActiveRooms();
        activeRooms.add(mockRoom);
        activeRooms.add(mockRoomOne);
        assertEquals(2, activeRooms.size());
        assertTrue(activeRooms.contains(mockRoom));
        assertTrue(activeRooms.contains(mockRoomOne));
    }

    @Test
    public void testContainsFalse() {
        Room mockRoom = mock(Room.class);
        Room mockRoomOne = mock(Room.class);

        ActiveRooms activeRooms = new ActiveRooms();
        activeRooms.add(mockRoom);
        assertEquals(1, activeRooms.size());
        assertTrue(activeRooms.contains(mockRoom));
        assertFalse(activeRooms.contains(mockRoomOne));
    }

    @Test
    public void testGetSpecificRoom() {
        Room mockRoom = mock(Room.class);
        UUID uuid = UUID.randomUUID();
        when(mockRoom.getId()).thenReturn(uuid);

        ActiveRooms activeRooms = new ActiveRooms();
        activeRooms.add(mockRoom);
        assertEquals(1, activeRooms.size());
        assertTrue(activeRooms.contains(mockRoom));
        Room returnedRoom = activeRooms.getRoom(uuid);
        assertEquals(mockRoom, returnedRoom);
    }

    @Test
    public void testGetListOfRooms() {
        Room mockRoom = mock(Room.class);
        UUID uuid = UUID.randomUUID();
        when(mockRoom.getId()).thenReturn(uuid);
        Room mockRoomOne = mock(Room.class);
        UUID uuidOne = UUID.randomUUID();
        when(mockRoomOne.getId()).thenReturn(uuidOne);
        Room mockRoomTwo = mock(Room.class);
        UUID uuidTwo = UUID.randomUUID();
        when(mockRoomTwo.getId()).thenReturn(uuidTwo);

        ActiveRooms activeRooms = new ActiveRooms();
        activeRooms.add(mockRoom);
        activeRooms.add(mockRoomOne);
        activeRooms.add(mockRoomTwo);

        Set<Room> roomList = activeRooms.getRooms();
        assertEquals(3, roomList.size());
        assertTrue(roomList.contains(mockRoom));
        assertTrue(roomList.contains(mockRoomOne));
        assertTrue(roomList.contains(mockRoomTwo));
    }

    @Test
    public void testResetActiveRooms() {
        ActiveRooms activeRooms = new ActiveRooms();

        Room roomOne = mock(Room.class);
        Room roomTwo = mock(Room.class);
        activeRooms.add(roomOne);
        activeRooms.add(roomTwo);
        assertEquals(2, activeRooms.size());

        activeRooms.removeAllRooms();
        assertEquals(0, activeRooms.size());
    }
}
