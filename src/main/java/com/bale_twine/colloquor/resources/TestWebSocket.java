package com.bale_twine.colloquor.resources;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public class TestWebSocket implements WebSocket.OnTextMessage {
    public static final String TEST_REPLY = "pong";
    public static final String TEST_REQEST = "ping";
    public static final String TEST_SALUTATION = "Hello!";

    private static final Logger LOGGER = LoggerFactory.getLogger(TestWebSocket.class);

    private final HttpSession session;

    private Connection connection;

    public TestWebSocket(HttpSession session) {
        this.session = session;
    }

    @Override
    public void onOpen(Connection connection) {

        try {
            connection.sendMessage(TEST_SALUTATION +" "+ session.getId());
        } catch (IOException e) {
            LOGGER.error("onOpen Failed", e);
        }

        this.connection = connection;
    }

    @Override
    public void onClose(int i, String s) {
        connection = null;
    }

    @Override
    public void onMessage(String s) {
        if (s.equalsIgnoreCase(TEST_REQEST)) {
            if (connection != null) {
                try {
                    connection.sendMessage(TEST_REPLY +" "+ session.getId());
                } catch (IOException e) {
                    LOGGER.error("onMessage Failed", e);
                }
            }
        }
    }
}
