package com.bale_twine.colloquor;

import com.bale_twine.colloquor.health.TemplateHealthCheck;
import com.bale_twine.colloquor.resources.*;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

public class ColloquorService extends Service<ColloquorConfiguration> {
    public static final String TEST_WEBSOCKET_ENDPOINT = "/web-socket-test";

    public static void main(String[] args) throws Exception {
        new ColloquorService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ColloquorConfiguration> bootstrap) {
        bootstrap.setName("colloquor");
		bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets/"));
    }

    @Override
    public void run(ColloquorConfiguration configuration,
                    Environment environment) {
        final String template = configuration.getTemplate();
        final String defaultName = configuration.getDefaultName();

        MongoDBClientManager dbClientManager = new MongoDBClientManager(
                configuration.getMongoUri(),
                configuration.getMongoUser(),
                configuration.getMongoPass());
        environment.manage(dbClientManager);
        environment.addResource(new HelloWorldResource(template, defaultName));
        environment.addResource(new HomeResource());
        environment.addResource(new TestResource(TEST_WEBSOCKET_ENDPOINT));
        environment.addServlet(new TestWebSocketServlet(), TEST_WEBSOCKET_ENDPOINT);
        environment.addHealthCheck(new TemplateHealthCheck(template));
    }

}
