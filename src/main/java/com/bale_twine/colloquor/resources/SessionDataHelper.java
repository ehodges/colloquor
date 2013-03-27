package com.bale_twine.colloquor.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionDataHelper {
    public static final String USERNAME_KEY = "username";
    public static final String DEFAULT_USERNAME = "Some Guy";

    private SessionDataHelper() {
    }

    public static String getUsername(HttpServletRequest request) {
        String name = (String) request.getSession().getAttribute(USERNAME_KEY);

        if(name == null)
            name = DEFAULT_USERNAME;

        return name;
    }

    public static void setUsername(HttpServletRequest request, String username) {
        HttpSession session = request.getSession();
        session.setAttribute(USERNAME_KEY, username);
    }
}
