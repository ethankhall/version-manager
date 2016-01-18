package io.ehdev.conrad.api.user.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUserRestController {

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }



}
