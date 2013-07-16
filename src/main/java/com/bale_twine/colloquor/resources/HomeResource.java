package com.bale_twine.colloquor.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.views.HomeView;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    private final MongoDBClientManager dbClientManager;

    public HomeResource(MongoDBClientManager dbClientManager) {
        this.dbClientManager = dbClientManager;
    }

    @GET
    public HomeView getHome(@Context HttpServletRequest request) {
        String guid = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(guid);

        return new HomeView(name);
    }
}