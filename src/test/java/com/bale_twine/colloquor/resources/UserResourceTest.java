package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.MongoDBClientManager;
import com.bale_twine.colloquor.api.User;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserResourceTest {

    private static final String TEST_GUID_ONE = "773fd569-9e61-4f61-ba61-db7c1b077101";

    @Test
    public void testAddUserName() {
        String name = "Ed";

        MongoDBClientManager mockDbClientManager = mock(MongoDBClientManager.class);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("guid")).thenReturn(TEST_GUID_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);

        UserResource userResource = new UserResource(mockDbClientManager);

        User user = userResource.updateUsername(mockRequest, new User(name));
        assertEquals(name, user.getName());
        verify(mockRequest).getSession();
        verify(mockDbClientManager).setUsername(TEST_GUID_ONE, name);
    }

    @Test
    public void testRetrieveUserName() {
        MongoDBClientManager mockDbClientManager = mock(MongoDBClientManager.class);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("guid")).thenReturn(TEST_GUID_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);

        when(mockDbClientManager.getUser(TEST_GUID_ONE)).thenReturn(new User("Ed"));

        UserResource userResource = new UserResource(mockDbClientManager);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Ed", user.getName());
    }

    @Test
    public void testRetrieveAlternateUserName() {
        MongoDBClientManager mockDbClientManager = mock(MongoDBClientManager.class);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("guid")).thenReturn(TEST_GUID_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockDbClientManager.getUser(TEST_GUID_ONE)).thenReturn(new User("Bob"));

        UserResource userResource = new UserResource(mockDbClientManager);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Bob", user.getName());
    }

    @Test
    public void testRetrieveDefaultUserName() {
        MongoDBClientManager mockDbClientManager = mock(MongoDBClientManager.class);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("guid")).thenReturn(TEST_GUID_ONE);
        when(mockRequest.getSession()).thenReturn(mockSession);
        when(mockDbClientManager.getUser(TEST_GUID_ONE)).thenReturn(new User("Some Guy"));

        UserResource userResource = new UserResource(mockDbClientManager);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Some Guy", user.getName());
    }
}
