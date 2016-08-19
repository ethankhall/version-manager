package tech.crom.webapp.endpoint

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class SigninController {

    @RequestMapping(value = "/signin", method = arrayOf(RequestMethod.GET))
    fun signin(): String = "signin"
}