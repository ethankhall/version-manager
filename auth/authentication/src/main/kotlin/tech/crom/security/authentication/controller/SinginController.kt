package tech.crom.security.authentication.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class SinginController() {

    @RequestMapping(value = "/signin", method = arrayOf(RequestMethod.GET))
    fun signIn() {
//        return "singin"
    }

    @RequestMapping(value = "/signup", method = arrayOf(RequestMethod.GET))
    fun signUp() {
//        return "singin"
    }
}
