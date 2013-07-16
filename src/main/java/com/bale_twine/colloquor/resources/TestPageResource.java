package com.bale_twine.colloquor.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.views.TestView;

import java.net.URI;

@Path("/test")
@Produces(MediaType.TEXT_HTML)
public class TestPageResource {
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int NO_HTTP_PORT_SPECIFIED = -1;
    public static final String PORT_SEPARATOR = ":";
    public static final String WEBSOCKET_CONNECTION_STRING_PATTERN = "ws://%s%s";

    @Context
    private static UriInfo uri;

    private final String websocketEndpoint;
    private final MongoDBClientManager dbClientManager;

    public TestPageResource(MongoDBClientManager dbClientManager, String websocketEndpoint) {
        this.dbClientManager = dbClientManager;
        this.websocketEndpoint = websocketEndpoint;
    }

    @GET
    public TestView getHome(@Context HttpServletRequest request) {
        URI myUri = uri.getBaseUri();
        StringBuilder serverConnection = new StringBuilder(myUri.getHost());
        int port = myUri.getPort();

        if(port != DEFAULT_HTTP_PORT && port != NO_HTTP_PORT_SPECIFIED)
            serverConnection.append(PORT_SEPARATOR + Integer.toString(port));

        String guid = SessionDataHelper.getGUID(request);
        String name = dbClientManager.getUsername(guid);

        return new TestView(name,
                String.format(WEBSOCKET_CONNECTION_STRING_PATTERN,
                        serverConnection.toString(),
                        websocketEndpoint)
        );
    }
}
