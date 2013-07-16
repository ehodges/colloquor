package com.bale_twine.colloquor.resources;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionDataHelperTest {

    @Test
    public void testGetNewGUID() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute(SessionDataHelper.GUID_KEY)).thenReturn(null);
        when(mockRequest.getSession()).thenReturn(mockSession);

        String guid = SessionDataHelper.getGUID(mockRequest);
        assertNotNull(guid);
        assertFalse(guid.isEmpty());
    }

    @Test
    public void testNewGUIDPersists() {
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        HttpSession mockSession = mock(HttpSession.class);

        when(mockSession.getAttribute(SessionDataHelper.GUID_KEY)).thenReturn(null);
        when(mockRequest.getSession()).thenReturn(mockSession);

        String guid = SessionDataHelper.getGUID(mockRequest);

        verify(mockSession).setAttribute(SessionDataHelper.GUID_KEY, guid);
    }
}
