package com.bale_twine.colloquor.api;

import org.hamcrest.Matcher;
import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.asJson;
import static com.yammer.dropwizard.testing.JsonHelpers.fromJson;
import static com.yammer.dropwizard.testing.JsonHelpers.jsonFixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class UserTest {

    public static final String TEST_USERNAME_ONE = "Bob";
    public static final String TEST_USERNAME_TWO = "Fred";

    @Test
    public void testUserConstructor() {
        User user = new User(TEST_USERNAME_ONE);
        assertEquals(TEST_USERNAME_ONE, user.getName());
    }

    @Test
    public void testUserConstructorAlternate() {
        User user = new User(TEST_USERNAME_TWO);
        assertEquals(TEST_USERNAME_TWO, user.getName());
    }

    @Test
    public void testUserSetterMethod() {
        User user = new User(TEST_USERNAME_TWO);
        user.setName(TEST_USERNAME_ONE);
        assertEquals(TEST_USERNAME_ONE, user.getName());
    }

    @Test
    public void testUserSetterMethodAlternate() {
        User user = new User(TEST_USERNAME_ONE);
        user.setName(TEST_USERNAME_TWO);
        assertEquals(TEST_USERNAME_TWO, user.getName());
    }

    @Test
    public void deserializesFromJSON() throws Exception {
        final User user = new User("Test Name", "ABC123", "123ABC");

        assertThat("parsing a valid API representation produces a user",
                fromJson(jsonFixture("fixtures/user.json"), User.class),
                is(user));
    }

    @Test
    public void serializesToJSON() throws Exception {
        final User user = new User("Test Name", "ABC123", "123ABC");
        assertThat("a User can be serialized to JSON",
                asJson(user),
                is(equalTo(jsonFixture("fixtures/user.json"))));
    }
}
