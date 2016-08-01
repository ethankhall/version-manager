package tech.crom.security.authentication.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.social.security.SocialUserDetails
import org.springframework.social.security.SocialUserDetailsService
import org.springframework.stereotype.Service
import tech.crom.database.api.UserManager
import tech.crom.logger.getLogger
import tech.crom.model.security.authentication.CromUserAuthentication
import java.util.*

@Service
class CromUserDetailsService @Autowired constructor(val userManager: UserManager): UserDetailsService, SocialUserDetailsService {
    val log by getLogger()

    override fun loadUserByUserId(userId: String?): SocialUserDetails {
        val user = userManager.findUserDetails(UUID.fromString(userId)) ?: throw UsernameNotFoundException("User $userId not found")

        return CromUserAuthentication.SecurityCromUser(user)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        log.info("Processing user $username")

        val user = userManager.findUserDetails(username) ?: throw UsernameNotFoundException("User $username not found")
        return CromUserAuthentication.SecurityCromUser(user)
    }
}
