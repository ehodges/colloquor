package com.bale_twine.colloquor.views;

import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.core.Room;
import com.bale_twine.colloquor.resources.RoomResource;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoomViewIT {
    private static final String TEST_USER_ONE = "John Cleese";
    private static final String TEST_USER_TWO = "Michael Palin";
    private static final String TEST_USER_THREE = "Terry Jones";

    @Test
    public void testRoomWithName() {
        final String TEST_ROOM_NAME = "A Test Room Name";

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        User user = new User(TEST_USER_ONE);
        Room room = new Room(user, TEST_ROOM_NAME);

        String id = room.getId().toString();
        activeRooms.add(room);

        RoomResource roomResource = new RoomResource();
        RoomView roomView = roomResource.getRoomView(mockRequest, id);

        assertEquals(TEST_ROOM_NAME, roomView.getName());
        assertEquals(id, roomView.getId());
        assertEquals(TEST_USER_ONE, roomView.getUserName());
    }

    @Test
    public void testRoomWithoutName() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        User user = new User(TEST_USER_ONE);
        Room room = new Room(user);

        String id = room.getId().toString();
        activeRooms.add(room);

        RoomResource roomResource = new RoomResource();
        RoomView roomView = roomResource.getRoomView(mockRequest, id);

        assertEquals(id, roomView.getId());
        assertEquals("Room " + id, roomView.getName());
        assertEquals(TEST_USER_ONE, roomView.getUserName());
    }

    @Test
    public void testEnteringRoomAddsUser() {
        HttpServletRequest mockRequestOne = mock(HttpServletRequest.class);
        HttpSession mockSessionOne = mock(HttpSession.class);

        HttpServletRequest mockRequestTwo = mock(HttpServletRequest.class);
        HttpSession mockSessionTwo = mock(HttpSession.class);

        when(mockSessionOne.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequestOne.getSession()).thenReturn(mockSessionOne);
        User userOne = new User(TEST_USER_ONE);

        when(mockSessionTwo.getAttribute("username")).thenReturn(TEST_USER_TWO);
        when(mockRequestTwo.getSession()).thenReturn(mockSessionTwo);
        User userTwo = new User(TEST_USER_TWO);

        User userThree = new User(TEST_USER_THREE);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        Room roomOne = new Room(userOne);

        String idOne = roomOne.getId().toString();

        activeRooms.add(roomOne);

        RoomResource rr = new RoomResource();
        RoomView rv = rr.getRoomView(mockRequestTwo, idOne);
        Set<User> occupants = rv.getOccupants();

        assertEquals(2, occupants.size());
        assertTrue(occupants.contains(userOne));
        assertTrue(occupants.contains(userTwo));
        assertFalse(occupants.contains(userThree));
    }

    @Test
    public void testCreatingRoomHasUser() {

        HttpServletRequest mockRequestOne = mock(HttpServletRequest.class);
        HttpSession mockSessionOne = mock(HttpSession.class);

        when(mockSessionOne.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequestOne.getSession()).thenReturn(mockSessionOne);
        User userOne = new User(TEST_USER_ONE);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        Room roomOne = new Room(userOne);

        String idOne = roomOne.getId().toString();

        activeRooms.add(roomOne);

        RoomResource rr = new RoomResource();

        RoomView rv = rr.getRoomView(mockRequestOne, idOne);
        Set<User> occupants = rv.getOccupants();
        assertEquals(1, occupants.size());
        assertTrue(occupants.contains(userOne));
    }

    @Test
    public void testEnteringRoomAddsNewUserOnly() {

        HttpServletRequest mockRequestOne = mock(HttpServletRequest.class);
        HttpSession mockSessionOne = mock(HttpSession.class);

        HttpServletRequest mockRequestTwo = mock(HttpServletRequest.class);
        HttpSession mockSessionTwo = mock(HttpSession.class);

        when(mockSessionOne.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequestOne.getSession()).thenReturn(mockSessionOne);
        User userOne = new User(TEST_USER_ONE);

        when(mockSessionTwo.getAttribute("username")).thenReturn(TEST_USER_TWO);
        when(mockRequestTwo.getSession()).thenReturn(mockSessionTwo);
        User userTwo = new User(TEST_USER_TWO);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        Room roomOne = new Room(userOne);
        Room roomTwo = new Room(userTwo);

        String idOne = roomOne.getId().toString();
        String idTwo = roomTwo.getId().toString();

        activeRooms.add(roomOne);
        activeRooms.add(roomTwo);

        RoomResource rr = new RoomResource();
        RoomView rv = rr.getRoomView(mockRequestTwo, idOne);
        Set<User> occupants = rv.getOccupants();
        assertEquals(2, occupants.size());

        rv = rr.getRoomView(mockRequestTwo, idOne);
        occupants = rv.getOccupants();
        assertEquals(2, occupants.size());

        rv = rr.getRoomView(mockRequestTwo, idTwo);
        occupants = rv.getOccupants();
        assertEquals(1, occupants.size());
    }

    /*
    @Test
    public void testEnteringRoomAddsNewUserOnly() {
        final String TEST_USER_ONE = "John Cleese";
        final String TEST_USER_TWO = "Michael Palin";

        HttpServletRequest mockRequestOne = mock(HttpServletRequest.class);
        HttpSession mockSessionOne = mock(HttpSession.class);

        HttpServletRequest mockRequestTwo = mock(HttpServletRequest.class);
        HttpSession mockSessionTwo = mock(HttpSession.class);

        when(mockSessionOne.getAttribute("username")).thenReturn(TEST_USER_ONE);
        when(mockRequestOne.getSession()).thenReturn(mockSessionOne);
        User userOne = new User(TEST_USER_ONE);

        when(mockSessionTwo.getAttribute("username")).thenReturn(TEST_USER_TWO);
        when(mockRequestTwo.getSession()).thenReturn(mockSessionTwo);
        User userTwo = new User(TEST_USER_TWO);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        Room roomOne = new Room(userOne);
        Room roomTwo = new Room(userTwo);

        String idOne = roomOne.getId().toString();
        String idTwo = roomTwo.getId().toString();

        activeRooms.add(roomOne);
        activeRooms.add(roomTwo);

        RoomResource rr = new RoomResource();
        RoomView rv = rr.getRoomView(mockRequestTwo, idOne);
        Set<User> occupants = rv.getOccupants();
        assertEquals(2, occupants.size());

        rv = rr.getRoomView(mockRequestTwo, idOne);
        occupants = rv.getOccupants();
        assertEquals(2, occupants.size());

        rv = rr.getRoomView(mockRequestOne, idOne);
        occupants = rv.getOccupants();
        assertEquals(1, occupants.size());
    }
    */
}
