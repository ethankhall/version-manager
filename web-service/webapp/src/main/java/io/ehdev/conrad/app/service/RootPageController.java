package io.ehdev.conrad.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping
public class RootPageController {

    @Autowired
    Environment environment;

    @RequestMapping("/")
    public Object testHomePage(Model model) {
        model.addAttribute("token", environment.getRequiredProperty("auth.client.github.key"));
        return "index";
    }
}
