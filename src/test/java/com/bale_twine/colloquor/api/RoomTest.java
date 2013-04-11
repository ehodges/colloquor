package com.bale_twine.colloquor.api;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomTest {

    @Test
    public void testGetRoomId() {
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid);
        assertEquals(uuid, room.getId());
    }

    @Test
    public void testOccupants() {
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid);
        Set<User> occupants = new HashSet<User>();
        occupants.add(new User("Bob"));
        room.setOccupants(occupants);
        assertEquals(occupants, room.getOccupants());
    }

    @Test
    public void testNoOccupants() {
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid);
        Set<User> occupants = room.getOccupants();
        assertEquals(0, occupants.size());
    }

    @Test
    public void testRoomsHaveTitlesWithConstructor() {
        String testTitle = "Bob's Room";
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid, testTitle);
        assertEquals(testTitle, room.getTitle());
    }

    @Test
    public void testRoomsHaveTitlesWithSetter() {
        String testTitle = "Bob's Room";
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid);
        room.setTitle(testTitle);
        assertEquals(testTitle, room.getTitle());
    }

    @Test
    public void testRoomsHaveTitlesWithDefault() {
        UUID uuid = UUID.randomUUID();

        Room room = new Room(uuid);

        assertEquals("Room " + uuid, room.getTitle());
    }
}
