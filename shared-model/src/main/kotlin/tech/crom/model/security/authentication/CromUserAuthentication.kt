package tech.crom.model.security.authentication

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.social.security.SocialUserDetails
import tech.crom.model.user.CromUser

data class CromUserAuthentication(val user: CromUser): CromAuthentication {

    val securityUser: SecurityCromUser

    init {
        securityUser = SecurityCromUser(user)
    }

    override fun getName(): String = user.userName

    override fun setAuthenticated(isAuthenticated: Boolean) { }

    override fun getCredentials(): Any? = null

    override fun isAuthenticated(): Boolean = true

    override fun getDetails(): CromUser = user

    override fun getAuthorities(): Collection<GrantedAuthority>? = securityUser.authorities

    override fun getPrincipal(): CromUser = user

    override fun getUniqueId(): String = user.userUid.toString()

    data class SecurityCromUser(val cromUser: CromUser): UserDetails, SocialUserDetails {
        override fun getUserId(): String = cromUser.userUid.toString()

        override fun getUsername(): String = cromUser.userName

        override fun isCredentialsNonExpired(): Boolean = true

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true

        override fun getAuthorities(): Collection<GrantedAuthority> = listOf(
            SimpleGrantedAuthority("ROLE_AUTHENTICATED"), SimpleGrantedAuthority("ROLE_USER"))

        override fun isEnabled(): Boolean = true

        override fun getPassword(): String = "<redacted>"
    }
}
