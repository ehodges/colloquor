package com.bale_twine.colloquor.views;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BaseUserViewTest {

    @Test
    public void testUsername() {
        final String testUsername = "Fred";
        BaseUserViewTestClass testViewClass = new BaseUserViewTestClass("bob.mustache", testUsername);
        assertEquals(testUsername, testViewClass.getUserName());
    }

    class BaseUserViewTestClass extends BaseUserView {
        protected BaseUserViewTestClass(String templateName, String userName) {
            super(templateName, userName);
        }
    }
}
