package com.bale_twine.colloquor.resources;

import org.eclipse.jetty.websocket.WebSocket;
import org.junit.Test;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestWebSocketTest {
    public static final String TEST_SALUTATION = "Hello!";
    public static final String TEST_REPLY = "pong";
    public static final String TEST_REQUEST = "ping";
    public static final String TEST_ID = "SomeKindOfTestId";

    @Test
    public void testOnOpen() throws Exception {
        HttpSession session = mock(HttpSession.class);

        when(session.getId()).thenReturn(TEST_ID);

        TestWebSocket testWebSocket = new TestWebSocket(session);
        WebSocket.Connection connection = mock(WebSocket.Connection.class);
        testWebSocket.onOpen(connection);
        verify(connection).sendMessage(TEST_SALUTATION + " " + TEST_ID);
    }

    @Test
    public void testOnMessage() throws Exception {
        HttpSession session = mock(HttpSession.class);

        when(session.getId()).thenReturn(TEST_ID);

        TestWebSocket testWebSocket = new TestWebSocket(session);
        WebSocket.Connection connection = mock(WebSocket.Connection.class);
        testWebSocket.onOpen(connection);
        testWebSocket.onMessage(TEST_REQUEST);
        verify(connection).sendMessage(TEST_REPLY + " " + TEST_ID);
    }
}
