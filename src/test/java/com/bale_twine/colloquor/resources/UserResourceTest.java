package com.bale_twine.colloquor.resources;

import com.bale_twine.colloquor.api.User;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class UserResourceTest {

    @Test
    public void testAddUserName() {
        UserResource userResource = new UserResource();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        String name = "Ed";

        when(mockRequest.getSession()).thenReturn(mockSession);

        User user = userResource.updateUsername(mockRequest, new User(name));
        assertEquals(name, user.getName());
        verify(mockRequest).getSession();
        verify(mockSession).setAttribute("username", name);
    }

    @Test
    public void testRetrieveUserName() {
        UserResource userResource = new UserResource();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn("Ed");
        when(mockRequest.getSession()).thenReturn(mockSession);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Ed", user.getName());
    }

    @Test
    public void testRetrieveAlternateUserName() {
        UserResource userResource = new UserResource();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn("Bob");
        when(mockRequest.getSession()).thenReturn(mockSession);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Bob", user.getName());
    }

    @Test
    public void testRetrieveDefaultUserName() {
        UserResource userResource = new UserResource();

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockRequest.getSession()).thenReturn(mockSession);

        User user = userResource.retrieveUserName(mockRequest);
        assertEquals("Some Guy", user.getName());
    }
}
