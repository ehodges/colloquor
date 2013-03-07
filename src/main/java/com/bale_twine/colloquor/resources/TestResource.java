package com.bale_twine.colloquor.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.bale_twine.colloquor.views.TestView;

import java.net.URI;
import java.util.List;

@Path("/test")
@Produces(MediaType.TEXT_HTML)
public class TestResource {
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final String PORT_SEPERATOR = ":";
    public static final String WEBSOCKET_CONNECTION_STRING_PATTERN = "ws://%s%s";

    @Context
    private static UriInfo uri;
    private final String websocketEndpoint;

    public TestResource(String websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
    }

    @GET
    public TestView getHome() {
        URI myUri = uri.getBaseUri();
        StringBuilder serverConnection = new StringBuilder(myUri.getHost());
        int port = myUri.getPort();

        if(port != DEFAULT_HTTP_PORT)
            serverConnection.append(PORT_SEPERATOR + Integer.toString(port));

        return new TestView(
                String.format(WEBSOCKET_CONNECTION_STRING_PATTERN,
                        serverConnection.toString(),
                        websocketEndpoint)
        );
    }
}
