package io.ehdev.conrad.api.user.rest;

import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUserRestController {

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public ConradUser user(ConradUser user) {
        return user;
    }
}
