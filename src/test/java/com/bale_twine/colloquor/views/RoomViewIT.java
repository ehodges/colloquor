package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.core.Room;
import com.bale_twine.colloquor.resources.RoomResource;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomViewIT {

    @Test
    public void testRoomWithName() {
        final String TEST_ROOM_NAME = "A Test Room Name";
        final String TEST_USERNAME = "A Test User Name";

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn(TEST_USERNAME);
        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        User user = new User(TEST_USERNAME);
        Room room = new Room(user, TEST_ROOM_NAME);

        String id = room.getId().toString();
        activeRooms.add(room);

        RoomResource roomResource = new RoomResource();
        RoomView roomView = roomResource.getRoomView(mockRequest, id);

        assertEquals(TEST_ROOM_NAME, roomView.getName());
        assertEquals(id, roomView.getId());
        assertEquals(TEST_USERNAME, roomView.getUserName());
    }

    @Test
    public void testRoomWithoutName() {
        final String TEST_USERNAME = "A Test User Name";

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn(TEST_USERNAME);
        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        User user = new User(TEST_USERNAME);
        Room room = new Room(user);

        String id = room.getId().toString();
        activeRooms.add(room);

        RoomResource roomResource = new RoomResource();
        RoomView roomView = roomResource.getRoomView(mockRequest, id);

        assertEquals(id, roomView.getId());
        assertEquals("Room " + id, roomView.getName());
        assertEquals(TEST_USERNAME, roomView.getUserName());
    }
}
