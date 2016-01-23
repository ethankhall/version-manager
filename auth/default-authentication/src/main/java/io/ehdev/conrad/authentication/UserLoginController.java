package io.ehdev.conrad.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserLoginController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String getSignInPage() {
        return "signin";
    }

    @RequestMapping("/signup")
    public void getSignUpPage() { }
}
