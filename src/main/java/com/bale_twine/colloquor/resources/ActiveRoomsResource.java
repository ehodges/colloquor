package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.api.Room;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.views.ActiveRoomView;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/activeRooms")
public class ActiveRoomsResource {

    private MongoDBClientManager dbClientManager;

    public ActiveRoomsResource (MongoDBClientManager dbClientManager) {
        this.dbClientManager = dbClientManager;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public ActiveRoomView getActiveRoomView(@Context HttpServletRequest request) {
        String guid = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(guid);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        Set<com.bale_twine.colloquor.core.Room> activeRoomsList = activeRooms.getRooms();
        List<Room> rooms = mapCoreRoomToAPIRoom(activeRoomsList);

        return new ActiveRoomView(name, rooms);
    }

    private List<Room> mapCoreRoomToAPIRoom(Set<com.bale_twine.colloquor.core.Room> activeRoomsList) {
        List<Room> rooms = new ArrayList<Room>();

        for (com.bale_twine.colloquor.core.Room room : activeRoomsList) {
            Room newRoom = new Room(room.getId());

            newRoom.setOccupants(room.getOccupants());  // Set Occupants Of The Room
            newRoom.setTitle(room.getTitle());          // Set The Room Title

            rooms.add(newRoom);
        }

        return rooms;
    }
}
