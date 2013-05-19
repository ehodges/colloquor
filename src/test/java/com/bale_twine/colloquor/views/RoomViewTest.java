package com.bale_twine.colloquor.views;


import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.Room;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RoomViewTest {

    @Test
    public void testGetRoomName() {
        final String roomName1 = "Test Room Name";
        RoomView roomView = new RoomView(UUID.randomUUID(), roomName1, "Fred");
        String roomName = roomView.getName();

        assertEquals(roomName1, roomName);
    }

    @Test
    public void testGetOccupants() {
        User userA = new User("Test Name A");
        User userB = new User("Test Name B");
        User userC = new User("Test Name C");

        Set<User> testUsers = new HashSet<User>();
        testUsers.add(userA);
        testUsers.add(userB);
        testUsers.add(userC);

        Room mockRoom = mock(Room.class);
        when(mockRoom.getOccupants()).thenReturn(testUsers);
        RoomView roomView = new RoomView(mockRoom, "Fred");
        Set<User> users = roomView.getOccupants();

        Iterator<User> inputIterator = testUsers.iterator();
        Iterator<User> outputIterator = users.iterator();

        while(inputIterator.hasNext()) {
            assertTrue(outputIterator.hasNext());
            User inputUser = inputIterator.next();
            User outputUser = outputIterator.next();

            assertEquals(inputUser.getName(), outputUser.getName());
        }
    }
}
