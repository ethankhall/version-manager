package io.ehdev.conrad.authentication.cookie;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserCookieManger {

    void addCookie(String contents, HttpServletResponse response);

    void removeCookie(HttpServletResponse response);

    String readCookieValue(HttpServletRequest request);
}
