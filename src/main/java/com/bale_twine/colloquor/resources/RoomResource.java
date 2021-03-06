package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.api.Room;
import com.bale_twine.colloquor.api.User;
import com.bale_twine.colloquor.core.ActiveRooms;
import com.bale_twine.colloquor.core.ActiveRoomsAccessor;
import com.bale_twine.colloquor.views.RoomView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Set;
import java.util.UUID;

@Path("/room/{id}")
public class RoomResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomResource.class);
    private final String websocketEndpoint;
    private final MongoDBClientManager dbClientManager;

    public RoomResource(String websocketEndpoint, MongoDBClientManager dbClientManager) {
        this.websocketEndpoint = websocketEndpoint;
        this.dbClientManager = dbClientManager;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Room createRoom(@Context HttpServletRequest request) {
        String guid = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(guid);

        com.bale_twine.colloquor.core.Room newRoom = new com.bale_twine.colloquor.core.Room(new User(name));
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        activeRooms.add(newRoom);

        Room room = new Room(newRoom.getId());

        Set<User> occupants = room.getOccupants();
        occupants.add(new User(name));
        room.setOccupants(occupants);

        return room;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoom(@PathParam("id") String id) {
        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();
        UUID uuid = UUID.fromString(id);
        com.bale_twine.colloquor.core.Room room = activeRooms.getRoom(uuid);

        Room thisRoom = new Room(room.getId());
        thisRoom.setOccupants(room.getOccupants());

        return thisRoom;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public RoomView getRoomView(@Context HttpServletRequest request, @PathParam("id") String id) {
        UUID uuid = UUID.fromString(id);

        ActiveRooms activeRooms = ActiveRoomsAccessor.getActiveRooms();

        String guid = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(guid);

        com.bale_twine.colloquor.core.Room room = activeRooms.getRoom(uuid);

        User user = new User(name);

        room.addUser(user);

        return new RoomView(room, name, websocketEndpoint);
    }

//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Room addUserToRoom(@Context HttpServletRequest request, @PathParam("id") UUID uuid) {
//    }
}
