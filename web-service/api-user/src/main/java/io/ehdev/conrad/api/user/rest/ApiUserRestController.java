package io.ehdev.conrad.api.user.rest;

import io.ehdev.conrad.api.user.database.BaseUserRepository;
import io.ehdev.conrad.model.user.ConradUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class ApiUserRestController {

    @Autowired
    BaseUserRepository baseUserRepository;

    @RequestMapping(path = "/details", method = RequestMethod.GET)
    public ConradUser user(ConradUser user) {
        return user;
    }
}
