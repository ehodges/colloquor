package com.bale_twine.colloquor.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bale_twine.colloquor.views.TestView;

import java.util.List;

@Path("/test")
@Produces(MediaType.TEXT_HTML)
public class TestResource {

    public TestResource() {
    }

    @GET
    public TestView getHome() {
        return new TestView();
    }
}