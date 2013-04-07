package com.bale_twine.colloquor.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActiveRoomsAccessorTests {

    @Test
    public void testActiveRoomsIsSingleInstance() {
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        ActiveRooms activeRoomsOne = ActiveRoomsAccessor.getActiveRooms();
        ActiveRooms activeRoomsTwo = ActiveRoomsAccessor.getActiveRooms();
        assertEquals(activeRooms, activeRoomsOne);
        assertEquals(activeRoomsOne, activeRoomsTwo);
    }
}
