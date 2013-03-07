package com.bale_twine.colloquor.resources;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import javax.servlet.http.HttpServletRequest;

public class TestWebSocketServlet extends WebSocketServlet {
    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
        return new TestWebSocket();
    }
}
