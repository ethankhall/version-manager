package io.ehdev.conrad.api.user.rest;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUserRestController {

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public Object user(Authentication user) {
        return user.getPrincipal();
    }
}
