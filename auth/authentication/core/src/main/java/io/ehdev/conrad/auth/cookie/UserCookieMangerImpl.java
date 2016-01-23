package io.ehdev.conrad.auth.cookie;

import org.springframework.stereotype.Service;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserCookieMangerImpl implements UserCookieManger {

    static final String COOKIE_NAME = "conrad_cookie";

    private final CookieGenerator userCookieGenerator = new CookieGenerator();

    public UserCookieMangerImpl() {
        this.userCookieGenerator.setCookieName(COOKIE_NAME);
    }

    @Override
    public void addCookie(String contents, HttpServletResponse response) {
        userCookieGenerator.addCookie(response, contents);
    }

    @Override
    public void removeCookie(HttpServletResponse response) {
        userCookieGenerator.addCookie(response, "");
    }

    @Override
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
