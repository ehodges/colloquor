package com.bale_twine.colloquor.api;

import org.junit.Test;

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
}
