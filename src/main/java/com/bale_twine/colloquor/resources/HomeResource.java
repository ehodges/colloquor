package com.bale_twine.colloquor.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.bale_twine.colloquor.views.HomeView;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class HomeResource {

    public HomeResource() {
    }

    @GET
    public HomeView getHome(@Context HttpServletRequest request) {
        String name = SessionDataHelper.getUsername(request);
        return new HomeView(name);
    }
}