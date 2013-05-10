package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.api.Room;
import com.bale_twine.colloquor.views.RoomView;
import org.junit.After;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomResourceTest {

    public static final String TEST_NAME = "Fred";

    @After
    public void clearAllRoomsFromActiveRooms() {
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        activeRooms.removeAllRooms();
    }

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

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        UUID uuid = UUID.randomUUID();
        when(newRoom.getId()).thenReturn(uuid);

        activeRooms.add(newRoom);
        RoomView roomView = roomResource.getRoomView(mockRequest, uuid.toString());
        assertEquals(uuid.toString(), roomView.getId());
    }

    @Test
    public void testCreateRoom() {
        RoomResource roomResource = new RoomResource();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn("Robert");
        when(mockRequest.getSession()).thenReturn(mockSession);

        Room room = roomResource.createRoom(mockRequest);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        assertEquals(1, activeRooms.size());
        assertEquals(room.getId(), activeRooms.getRooms().iterator().next().getId());
        assertEquals("Robert", room.getOccupants().iterator().next().getName());
    }
}
