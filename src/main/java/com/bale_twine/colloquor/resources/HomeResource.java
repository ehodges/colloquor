package com.bale_twine.colloquor.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bale_twine.colloquor.views.HomeView;
import com.mongodb.MongoClient;

import java.util.List;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    private final MongoClient mongoClient;

    public HomeResource(MongoClient mc) {
        this.mongoClient = mc;
    }

    @GET
    public HomeView getHome() {
        return new HomeView(mongoClient.getDatabaseNames());
    }
}