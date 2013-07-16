package com.bale_twine.colloquor;

import com.mongodb.*;
import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoDBClientManager implements Managed {
    private static final String DB_NAME = "colloquor";

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClientManager.class);
    public static final String DEFAULT_USERNAME = "Some Guy";
    public static final String USERS_COLLECTION_KEY = "users";
    public static final String USER_GUID_KEY = "guid";
    public static final String USERNAME_KEY = "username";

    private MongoClient mongoClient;
    private DB colloquorDB;

    public MongoDBClientManager(String mongoUri, String username, String password) throws UnknownHostException {
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

    public String getUsername(String GUID) {
        DBCollection usersCollection = colloquorDB.getCollection(USERS_COLLECTION_KEY);
        DBObject queryObject = new BasicDBObject(USER_GUID_KEY, GUID);
        DBCursor cursor = usersCollection.find(queryObject);

        int num = cursor.count();

        String name = DEFAULT_USERNAME;

        if (num > 0) {
            name = (String) cursor.next().get(USERNAME_KEY);
        }

        return name;
    }

    public void setUsername(String GUID, String userName) {
        DBCollection usersCollection = colloquorDB.getCollection(USERS_COLLECTION_KEY);
        DBObject insertObject = new BasicDBObject(USER_GUID_KEY, GUID).append(USERNAME_KEY, userName);
        usersCollection.insert(insertObject);
    }
}
