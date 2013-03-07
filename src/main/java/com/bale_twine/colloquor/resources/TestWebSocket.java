package com.bale_twine.colloquor.resources;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestWebSocket implements WebSocket.OnTextMessage {
    public static final String TEST_REPLY = "pong";
    public static final String TEST_REQEST = "ping";
    public static final String TEST_SALUTATION = "Hello!";

    private static final Logger LOGGER = LoggerFactory.getLogger(TestWebSocket.class);

    Connection m_connection;

    @Override
    public void onOpen(Connection connection) {
        try {
            connection.sendMessage(TEST_SALUTATION);
        } catch (IOException e) {
            LOGGER.error("onOpen Failed", e);
        }

        m_connection = connection;
    }

    @Override
    public void onClose(int i, String s) {
        m_connection = null;
    }

    @Override
    public void onMessage(String s) {
        if (s.equalsIgnoreCase(TEST_REQEST)) {
            if (m_connection != null) {
                try {
                    m_connection.sendMessage(TEST_REPLY);
                } catch (IOException e) {
                    LOGGER.error("onMessage Failed", e);
                }
            }
        }
    }
}
