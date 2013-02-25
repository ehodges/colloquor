package com.bale_twine.colloquor;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoDBClientManager implements Managed {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClientManager.class);

    private final String mongoUri;
    private final String username;
    private final String password;
    private MongoClient mongoClient;

    public MongoDBClientManager(String mongoUri, String username, String password) {
        this.mongoUri = mongoUri;
        this.username = username;
        this.password = password;
    }

    @Override
    public void start() throws Exception {
        MongoClientURI mongoClientURI = new MongoClientURI(mongoUri);
        mongoClient = new MongoClient(mongoClientURI);
        LOGGER.warn(mongoClient.debugString());
        DB colloquorDB = mongoClient.getDB("colloquor");
        colloquorDB.authenticate(username, password.toCharArray());
        LOGGER.warn(colloquorDB.getName());
    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }

    public MongoClient getClient() {
        return mongoClient;
    }
}
