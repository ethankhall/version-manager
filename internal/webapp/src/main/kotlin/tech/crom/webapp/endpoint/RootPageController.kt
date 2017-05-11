package tech.crom.webapp.endpoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.view.RedirectView
import tech.crom.security.authentication.getRootPage

@Service
@RequestMapping
open class RootPageController @Autowired constructor(val environment: Environment) {

    @RequestMapping(value = "/", produces = arrayOf("application/xml", "application/json"))
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    fun rootPage(): Map<String, String> {
        return mapOf(Pair("docs", "https://github.com/ethankhall/version-manager-clients"))
    }

    @RequestMapping(value = "/")
    fun rootPageRedirect(): RedirectView = RedirectView(environment.getRootPage())
}
