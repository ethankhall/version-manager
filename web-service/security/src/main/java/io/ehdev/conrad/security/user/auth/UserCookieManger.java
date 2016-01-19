package io.ehdev.conrad.security.user.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserCookieManger {

    void addCookie(String contents, HttpServletResponse response);

    void removeCookie(HttpServletResponse response);

    String readCookieValue(HttpServletRequest request);
}
