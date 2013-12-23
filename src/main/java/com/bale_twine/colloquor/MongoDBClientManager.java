package com.bale_twine.colloquor;

import com.bale_twine.colloquor.api.User;
import com.mongodb.*;
import com.yammer.dropwizard.lifecycle.Managed;
import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;

public class MongoDBClientManager implements Managed {
    private static final String DB_NAME = "colloquor";

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBClientManager.class);
    public static final String DEFAULT_USERNAME = "Some Guy";
    public static final String USERS_COLLECTION_KEY = "users";
    public static final String USER_GUID_KEY = "guid";
    public static final WriteConcern WRITE_CONCERN = WriteConcern.SAFE;

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

    public DB getDB() {
        return colloquorDB;
    }

    public String getUsername(String GUID) {
        User user = getUser(GUID);
        return user.getName();
    }

    public void setUsername(String GUID, String userName) {
        User existingUser = getUser(GUID);

        if (existingUser != null) {
            existingUser.setName(userName);
            updateUser(existingUser);
        }
    }

    public User getUser(String GUID) {
        JacksonDBCollection<User, String> wrappedCollection = getWrappedUserCollection();

        org.mongojack.DBCursor<User> usersResult = wrappedCollection.find(DBQuery.is(USER_GUID_KEY, GUID));

        if (usersResult.count() == 1) {
            return usersResult.next();
        } else {
            return new User(DEFAULT_USERNAME, GUID);
        }
    }

    private boolean addUser(User user) {
        JacksonDBCollection<User, String> wrappedCollection = getWrappedUserCollection();

        WriteResult<User, String> result = wrappedCollection.insert(user, WRITE_CONCERN);

        return result.getLastError().ok();
    }

    private boolean updateUser(User user) {
        JacksonDBCollection<User, String> wrappedCollection = getWrappedUserCollection();

        WriteResult<User, String> result =
                wrappedCollection.update(DBQuery.is(USER_GUID_KEY, user.getGuid()),
                        user,
                        true,
                        false,
                        WRITE_CONCERN);

        return result.getLastError().ok();
    }

    private JacksonDBCollection<User, String> getWrappedUserCollection() {
        DBCollection usersCollection = colloquorDB.getCollection(USERS_COLLECTION_KEY);
        return JacksonDBCollection.wrap(usersCollection, User.class, String.class);
    }
}
