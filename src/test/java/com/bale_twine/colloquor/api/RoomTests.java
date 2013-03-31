package com.bale_twine.colloquor.api;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomTests {

    public static final String TEST_USERNAME = "Ed";
    public static final String TEST_USERNAME_ONE = "Bob";

    @Test
    public void testCreateAndGetRoom() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn(TEST_USERNAME);
        Room testRoom = new Room(mockUser);
        Set<User> occupants = testRoom.getOccupants();
        assertTrue(occupants.contains(mockUser));
        assertEquals(1, occupants.size());
        assertEquals(TEST_USERNAME, occupants.iterator().next().getName());
    }

    @Test
    public void testCreateRoomAndAddUser() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn(TEST_USERNAME);
        User mockUserOne = mock(User.class);
        when(mockUserOne.getName()).thenReturn(TEST_USERNAME_ONE);
        Room testRoom = new Room(mockUser);
        testRoom.addUser(mockUserOne);
        Set<User> occupants = testRoom.getOccupants();
        assertTrue(occupants.contains(mockUser));
        assertTrue(occupants.contains(mockUserOne));
        assertEquals(2, occupants.size());
        Iterator<User> occupantsIterator = occupants.iterator();

        boolean foundUserOne = false;
        boolean foundUserTwo = false;

        while(occupantsIterator.hasNext()) {
            User user = occupantsIterator.next();

            if(user.getName().equals(TEST_USERNAME)) {
                foundUserOne = true;
            } else if(user.getName().equals(TEST_USERNAME_ONE)) {
                foundUserTwo = true;
            }

        }
        assertTrue(foundUserOne);
        assertTrue(foundUserTwo);
    }

    @Test
    public void testRoomIsASetOfUsers() {
        User mockUser = mock(User.class);
        when(mockUser.getName()).thenReturn(TEST_USERNAME);
        Room testRoom = new Room(mockUser);
        testRoom.addUser(mockUser);
        testRoom.addUser(mockUser);
        Set<User> occupants = testRoom.getOccupants();
        assertEquals(1, occupants.size());
    }
}
