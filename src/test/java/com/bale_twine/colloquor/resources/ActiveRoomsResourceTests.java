package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.api.Room;
import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.views.ActiveRoomView;
import org.junit.After;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActiveRoomsResourceTests {

    @After
    public void clearAllRoomsFromActiveRooms() {
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        activeRooms.removeAllRooms();
    }

    @Test
    public void testGetActiveRoomsWithNoRooms() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRoomsResource activeRoomsResource = new ActiveRoomsResource();
        ActiveRoomView activeRoomView = activeRoomsResource.getActiveRoomView(mockRequest);

        List<Room> rooms = activeRoomView.getRooms();
        assertEquals(0, rooms.size());
    }

    @Test
    public void testGetActiveRoomsWithOneRoom() {
        String title = "Test Room Title";
        UUID uuid = UUID.randomUUID();

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        com.bale_twine.colloquor.core.Room testRoom = mock(com.bale_twine.colloquor.core.Room.class);

        when(testRoom.getId()).thenReturn(uuid);
        when(testRoom.getTitle()).thenReturn(title);
        Set<User> users = new HashSet<User>();
        users.add(mock(User.class));
        when(testRoom.getOccupants()).thenReturn(users);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockRequest.getSession()).thenReturn(mockSession);

        activeRooms.add(testRoom);

        ActiveRoomsResource activeRoomsResource = new ActiveRoomsResource();
        ActiveRoomView activeRoomView = activeRoomsResource.getActiveRoomView(mockRequest);

        List<Room> rooms = activeRoomView.getRooms();
        assertEquals(1, rooms.size());
        Room returnedRoom = rooms.get(0);
        assertEquals(uuid, returnedRoom.getId());
        assertEquals(users, returnedRoom.getOccupants());
        assertEquals(title, returnedRoom.getTitle());
    }

    @Test
    public void testGetActiveRoomsWithTwoRoom() {
        String title = "Test Room Title";
        String titleOne = "Test Room Title One";

        UUID uuid = UUID.randomUUID();
        UUID uuidOne = UUID.randomUUID();

        com.bale_twine.colloquor.core.Room testRoom = mock(com.bale_twine.colloquor.core.Room.class);
        com.bale_twine.colloquor.core.Room testRoomOne = mock(com.bale_twine.colloquor.core.Room.class);

        when(testRoom.getId()).thenReturn(uuid);
        when(testRoomOne.getId()).thenReturn(uuidOne);
        Set<User> users = new HashSet<User>();
        users.add(mock(User.class));
        when(testRoom.getOccupants()).thenReturn(users);
        when(testRoomOne.getOccupants()).thenReturn(users);

        when(testRoom.getTitle()).thenReturn(title);
        when(testRoomOne.getTitle()).thenReturn(titleOne);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        activeRooms.add(testRoom);
        activeRooms.add(testRoomOne);

        ActiveRoomsResource activeRoomsResource = new ActiveRoomsResource();
        ActiveRoomView activeRoomView = activeRoomsResource.getActiveRoomView(mockRequest);

        List<Room> rooms = activeRoomView.getRooms();
        assertEquals(2, rooms.size());

        for (Room currentRoom : rooms) {
            assertTrue(
                    uuid.equals(currentRoom.getId()) ||
                    uuidOne.equals(currentRoom.getId())
            );

            if(uuid.equals(currentRoom.getId())) {
                assertEquals(title, currentRoom.getTitle());
                assertEquals(users, currentRoom.getOccupants());
            } else {
                assertEquals(titleOne, currentRoom.getTitle());
                assertEquals(users, currentRoom.getOccupants());
            }
        }
    }
}
