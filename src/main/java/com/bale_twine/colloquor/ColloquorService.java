package com.bale_twine.colloquor;

import com.bale_twine.colloquor.health.TemplateHealthCheck;
import com.bale_twine.colloquor.resources.HelloWorldResource;
import com.bale_twine.colloquor.resources.HomeResource;
import com.bale_twine.colloquor.resources.TestResource;
import com.bale_twine.colloquor.resources.TestWebSocketServlet;
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
    public static final int ONE_HOUR = 3600;
    private static final String SESSION_DB_COLLECTION = "sessions";
    private static final String TEST_WEBSOCKET_ENDPOINT = "/web-socket-test";

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

        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addResource(new HomeResource());
        environment.addResource(new TestResource(TEST_WEBSOCKET_ENDPOINT));
        environment.addServlet(new TestWebSocketServlet(), TEST_WEBSOCKET_ENDPOINT);
        environment.addHealthCheck(new TemplateHealthCheck(template));
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
        String hostname = address.getHostName();

        SessionManager sessionManager = null;
        MongoSessionIdManager sessionIdManager = null;

        Server server = new Server(configuration.getHttpConfiguration().getPort());

        try {
            sessionManager = new MongoSessionManager();
            sessionIdManager = new MongoSessionIdManager(server, mongoDB.getCollection(SESSION_DB_COLLECTION));
        } catch (UnknownHostException e) {
            LOGGER.error("Unable to setup mongo session management.", e);
            throw e;
        }

        sessionIdManager.setScavengeDelay(ONE_HOUR);
        sessionIdManager.setWorkerName(hostname);
        server.setSessionIdManager(sessionIdManager);
        sessionManager.setSessionIdManager(server.getSessionIdManager());

        return new SessionHandler(sessionManager);
    }

}
