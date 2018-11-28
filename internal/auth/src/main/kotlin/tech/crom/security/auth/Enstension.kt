package tech.crom.security.auth

import org.springframework.core.env.Environment

fun Environment.getRootPage(): String {
    val domain = this.getRequiredProperty("web-ui.root-domain")
    val page = this.getRequiredProperty("web-ui.root-page")

    return "$domain/${page.trimStart('/')}"
}
