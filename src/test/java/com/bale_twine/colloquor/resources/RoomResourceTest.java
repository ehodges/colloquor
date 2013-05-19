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
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RoomResourceTest {

    public static final String TEST_USERNAME_ONE = "Graham Chapman";
    private static final String TEST_USERNAME_TWO = "Eric Idle";

    @After
    public void clearAllRoomsFromActiveRooms() {
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        activeRooms.removeAllRooms();
    }

    @Test
    public void testGetExistingRoom() {
        RoomResource roomResource = new RoomResource();

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        com.bale_twine.colloquor.core.Room newRoom = new com.bale_twine.colloquor.core.Room(new User(TEST_USERNAME_ONE));
        UUID uuid = newRoom.getId();
        activeRooms.add(newRoom);
        Room retrievedRoom = roomResource.getRoom(uuid.toString());

        assertEquals(TEST_USERNAME_ONE, retrievedRoom.getOccupants().iterator().next().getName());
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

        when(mockSession.getAttribute("username")).thenReturn(TEST_USERNAME_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);

        Room room = roomResource.createRoom(mockRequest);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        assertEquals(1, activeRooms.size());
        assertEquals(room.getId(), activeRooms.getRooms().iterator().next().getId());
        assertEquals(TEST_USERNAME_ONE, room.getOccupants().iterator().next().getName());
    }

    @Test
    public void testGetRoomAddsUser() {
        RoomResource roomResource = new RoomResource();

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        com.bale_twine.colloquor.core.Room mockRoom = mock(com.bale_twine.colloquor.core.Room.class);

        User userOne = mock(User.class);

        when(userOne.getName()).thenReturn(TEST_USERNAME_ONE);

        HttpSession mockSession = mock(HttpSession.class);
        when(mockSession.getAttribute("username")).thenReturn(TEST_USERNAME_ONE);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        UUID uuid = UUID.randomUUID();
        when(mockRoom.getId()).thenReturn(uuid);
        Set<User> occupants = new HashSet<User>();
        occupants.add(userOne);
        when(mockRoom.getOccupants()).thenReturn(occupants);

        activeRooms.add(mockRoom);
        RoomView roomView = roomResource.getRoomView(mockRequest, uuid.toString());
        assertEquals(uuid.toString(), roomView.getId());
        assertEquals(1, roomView.getOccupants().size());

        HttpSession mockSessionTwo = mock(HttpSession.class);
        when(mockSessionTwo.getAttribute("username")).thenReturn(TEST_USERNAME_TWO);

        HttpServletRequest mockRequestTwo = mock(HttpServletRequest.class);
        when(mockRequestTwo.getSession()).thenReturn(mockSessionTwo);

        roomResource.getRoomView(mockRequestTwo, uuid.toString());

        verify(mockRoom, times(2)).addUser(isA(User.class));
    }
}
