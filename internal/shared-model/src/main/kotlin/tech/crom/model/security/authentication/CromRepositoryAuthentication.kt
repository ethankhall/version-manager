package tech.crom.model.security.authentication

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import tech.crom.model.repository.CromRepo

data class CromRepositoryAuthentication(val source: CromRepo) : CromAuthentication {

    val securityRepo: SecurityCromRepo

    init {
        securityRepo = SecurityCromRepo(source)
    }

    override fun getName(): String = source.repoName

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getCredentials(): Any? = null

    override fun isAuthenticated(): Boolean = true

    override fun getDetails(): CromRepo = source

    override fun getAuthorities(): Collection<GrantedAuthority>? = securityRepo.authorities

    override fun getPrincipal(): CromRepo = source

    override fun getUniqueId(): String = source.repoId.toString()

    data class SecurityCromRepo(val cromRepo: CromRepo) : UserDetails {

        override fun getUsername(): String = cromRepo.repoName

        override fun isCredentialsNonExpired(): Boolean = true

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true

        override fun getAuthorities(): Collection<GrantedAuthority> = listOf(
            SimpleGrantedAuthority("ROLE_AUTHENTICATED"), SimpleGrantedAuthority("ROLE_API"))

        override fun isEnabled(): Boolean = true

        override fun getPassword(): String = "<redacted>"
    }
}
