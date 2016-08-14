package tech.crom

import org.springframework.security.core.context.SecurityContextHolder
import tech.crom.model.security.authentication.CromAuthentication
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime

fun Instant.toZonedDateTime(): ZonedDateTime = ZonedDateTime.ofInstant(this, ZoneOffset.UTC)

fun getCromAuthentication(): CromAuthentication {
    val authentication = SecurityContextHolder.getContext().authentication
    return when(authentication) {
        is CromAuthentication -> authentication
        else -> throw RuntimeException("Authentication could not be found.")
    }
}
