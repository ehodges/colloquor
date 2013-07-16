package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);
    private final MongoDBClientManager dbClientManager;

    public UserResource(MongoDBClientManager dbClientManager) {
        this.dbClientManager = dbClientManager;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUsername(@Context HttpServletRequest request, @Valid User user) {
        String userGUID = SessionDataHelper.getGUID(request);
        dbClientManager.setUsername(userGUID, user.getName());

        return user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User retrieveUserName(@Context HttpServletRequest request) {
        String userGUID = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(userGUID);
        return new User(name);
    }
}
