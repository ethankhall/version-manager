package tech.crom

import org.springframework.security.core.context.SecurityContextHolder
import tech.crom.model.security.authentication.CromAuthentication
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.user.CromUser
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

fun Instant.toZonedDateTime(): ZonedDateTime = ZonedDateTime.ofInstant(this, ZoneOffset.UTC)

fun findCromAuthentication(): CromAuthentication? {
    val authentication = SecurityContextHolder.getContext().authentication
    return when(authentication) {
        is CromAuthentication -> authentication
        else -> null
    }
}

fun findCromUser(): CromUser? {
    val authentication = SecurityContextHolder.getContext().authentication
    return if (authentication is CromUserAuthentication) authentication.user else null
}
