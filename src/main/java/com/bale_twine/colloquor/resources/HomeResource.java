package com.bale_twine.colloquor.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bale_twine.colloquor.views.HomeView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    public HomeResource() {
    }

    @GET
    public HomeView getHome() {
        return new HomeView();
    }
}