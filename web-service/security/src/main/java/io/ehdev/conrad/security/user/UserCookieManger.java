package io.ehdev.conrad.security.user;

import org.springframework.stereotype.Service;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserCookieManger {

    static final String COOKIE_NAME = "conrad_cookie";

    private final CookieGenerator userCookieGenerator = new CookieGenerator();

    public UserCookieManger() {
        this.userCookieGenerator.setCookieName(COOKIE_NAME);
    }

    public void addCookie(String contents, HttpServletResponse response) {
        userCookieGenerator.addCookie(response, contents);
    }

    public void removeCookie(HttpServletResponse response) {
        userCookieGenerator.addCookie(response, "");
    }

    public String readCookieValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieGenerator.getCookieName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
