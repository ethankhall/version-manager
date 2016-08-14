package io.ehdev.conrad.service.api.service.user;

import io.ehdev.conrad.service.api.aop.annotation.LoggedInUserRequired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/user")
public class UserEndpoint {

    @LoggedInUserRequired
    @RequestMapping(value = "/profile")
    public ResponseEntity findProfile() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
