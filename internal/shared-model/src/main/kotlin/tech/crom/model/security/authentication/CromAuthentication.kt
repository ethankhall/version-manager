package tech.crom.model.security.authentication

import org.springframework.security.core.Authentication

interface CromAuthentication : Authentication {
    fun getUniqueId(): String
}
