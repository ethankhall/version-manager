package io.ehdev.conrad.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class UserLoginController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String getSignInPage() {
        return "signin";
    }

    @RequestMapping("/signup")
    public void getSignUpPage() { }

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public @ResponseBody Principal user(Principal user) {
        return user;
    }
}
