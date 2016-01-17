package io.ehdev.conrad.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping
public class RootPageController {
    @RequestMapping("/")
    public Object testHomePage() {
        return "index";
    }
}
