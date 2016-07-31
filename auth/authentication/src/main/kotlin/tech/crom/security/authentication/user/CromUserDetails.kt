package tech.crom.security.authentication.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.social.security.SocialUserDetails

class CromUserDetails(val userName: String, val id: String): UserDetails, SocialUserDetails {

    override fun getUserId(): String = id

    override fun getUsername(): String = userName

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isEnabled(): Boolean = true

    override fun getPassword(): String = "<redacted>"

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return listOf(SimpleGrantedAuthority("USER"))
    }

}
