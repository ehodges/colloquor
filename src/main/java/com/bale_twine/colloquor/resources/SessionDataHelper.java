package com.bale_twine.colloquor.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class SessionDataHelper {

    protected static final String GUID_KEY = "guid";

    private SessionDataHelper() {
    }

    public static String getGUID(HttpServletRequest request) {
        String guid = (String) request.getSession().getAttribute(GUID_KEY);

        if(guid == null) {
            guid = UUID.randomUUID().toString();
            request.getSession().setAttribute(GUID_KEY, guid);
        }

        return guid;
    }
}
