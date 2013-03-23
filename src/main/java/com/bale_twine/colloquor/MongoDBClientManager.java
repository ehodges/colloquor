package com.bale_twine.colloquor;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoDBClientManager implements Managed {
    private static final String DB_NAME = "colloquor";

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClientManager.class);

    private final String mongoUri;
    private final String username;
    private final String password;
    private MongoClient mongoClient;
    private DB colloquorDB;

    public MongoDBClientManager(String mongoUri, String username, String password) throws UnknownHostException {
        this.mongoUri = mongoUri;
        this.username = username;
        this.password = password;

        MongoClientURI mongoClientURI = new MongoClientURI(mongoUri);
        mongoClient = new MongoClient(mongoClientURI);
        LOGGER.warn(mongoClient.debugString());
        colloquorDB = mongoClient.getDB(DB_NAME);
        colloquorDB.authenticate(username, password.toCharArray());
        LOGGER.warn(colloquorDB.getName());
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }

    public MongoClient getClient() {
        return mongoClient;
    }

    public DB getDB() {
        return colloquorDB;
    }
}
