package com.bale_twine.colloquor;

import com.bale_twine.colloquor.health.TemplateHealthCheck;
import com.bale_twine.colloquor.resources.*;
import com.mongodb.DB;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import org.eclipse.jetty.nosql.mongodb.MongoSessionIdManager;
import org.eclipse.jetty.nosql.mongodb.MongoSessionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ColloquorService extends Service<ColloquorConfiguration> {
    private static final String ASSETS_PATH = "/assets/";
    public static final String MATCH_PERIOD_REGEX = "\\.";
    public static final int ONE_HOUR = 3600;
    private static final String SESSION_DB_COLLECTION = "sessions";
    private static final String TEST_WEBSOCKET_ENDPOINT = "/web-socket-test";
    private static final String ROOM_WEBSOCKET_ENDPOINT = "/web-socket-room";

    private static final Logger LOGGER = LoggerFactory.getLogger(ColloquorService.class);

    public static void main(String[] args) throws Exception {
        new ColloquorService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ColloquorConfiguration> bootstrap) {
        bootstrap.setName("colloquor");
		bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle(ASSETS_PATH, ASSETS_PATH));
    }

    @Override
    public void run(ColloquorConfiguration configuration,
                    Environment environment) throws UnknownHostException {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();

        MongoDBClientManager dbClientManager = getMongoDBClientManager(configuration);
        environment.manage(dbClientManager);

        SessionHandler sessionHandler = getSessionHandler(configuration, dbClientManager);
        environment.setSessionHandler(sessionHandler);

        addResources(environment, template, defaultName, dbClientManager);

        environment.addServlet(new TestWebSocketServlet(), TEST_WEBSOCKET_ENDPOINT);
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

    private void addResources(Environment environment, String template, String defaultName, MongoDBClientManager dbClientManager) {
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addResource(new HomeResource(dbClientManager));
        environment.addResource(new TestPageResource(dbClientManager, TEST_WEBSOCKET_ENDPOINT));
        environment.addResource(new UserResource(dbClientManager));
        environment.addResource(new ActiveRoomsResource(dbClientManager));
        environment.addResource(new RoomResource(ROOM_WEBSOCKET_ENDPOINT, dbClientManager));
    }

    private MongoDBClientManager getMongoDBClientManager(ColloquorConfiguration configuration) throws UnknownHostException {
        return new MongoDBClientManager(
                    configuration.getMongoUri(),
                    configuration.getMongoUser(),
                    configuration.getMongoPass());
    }

    private SessionHandler getSessionHandler(ColloquorConfiguration configuration,
                                             MongoDBClientManager dbClientManager)
            throws UnknownHostException {

        DB mongoDB = dbClientManager.getDB();

        InetAddress address = InetAddress.getLocalHost();
        String hostname = address.getHostName().replaceAll(MATCH_PERIOD_REGEX, "_");

        SessionManager sessionManager;
        MongoSessionIdManager sessionIdManager;

        Server server = new Server(configuration.getHttpConfiguration().getPort());

        try {
            sessionManager = new MongoSessionManager();
            sessionIdManager = new MongoSessionIdManager(server, mongoDB.getCollection(SESSION_DB_COLLECTION));
        } catch (UnknownHostException exception) {
            LOGGER.error("Unable to setup mongo session management.", exception);
            throw exception;
        }

        sessionIdManager.setScavengeDelay(ONE_HOUR);
        sessionIdManager.setWorkerName(hostname);
        server.setSessionIdManager(sessionIdManager);
        sessionManager.setSessionIdManager(server.getSessionIdManager());

        return new SessionHandler(sessionManager);
    }

}
