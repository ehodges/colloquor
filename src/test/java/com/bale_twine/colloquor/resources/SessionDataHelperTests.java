package com.bale_twine.colloquor.resources;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionDataHelperTests {

    @Test
    public void testGetUsername() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute("username")).thenReturn("Ed");
        when(mockRequest.getSession()).thenReturn(mockSession);

        String username = SessionDataHelper.getUsername(mockRequest);
        assertEquals("Ed", username);
    }

    @Test
    public void testGetUsernameReturnsDefault() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        String username = SessionDataHelper.getUsername(mockRequest);
        assertEquals("Some Guy", username);
    }

    @Test
    public void testSetUsername() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);
        when(mockRequest.getSession()).thenReturn(mockSession);

        String username = "Bob";
        SessionDataHelper.setUsername(mockRequest, username);
        verify(mockRequest).getSession();
        verify(mockSession).setAttribute("username", username);
    }
}
