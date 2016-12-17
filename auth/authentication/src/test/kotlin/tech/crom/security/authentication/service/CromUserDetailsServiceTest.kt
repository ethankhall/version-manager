package tech.crom.security.authentication.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import org.springframework.security.core.userdetails.UsernameNotFoundException
import tech.crom.database.api.UserManager
import tech.crom.security.authentication.createUser
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class CromUserDetailsServiceTest: Spek({
    val userManager: UserManager = mock()
    val uds = CromUserDetailsService(userManager)

    context("accessing user") {
        it("will throw when userid is bad") {

            assertFailsWith(UsernameNotFoundException::class) {
                whenever(userManager.findUserDetails(any<UUID>())).thenReturn(null)
                uds.loadUserByUserId(UUID.randomUUID().toString())
            }

            assertFailsWith(UsernameNotFoundException::class) {
                whenever(userManager.findUserDetails(any<String>())).thenReturn(null)
                uds.loadUserByUsername("")
            }
        }

        it("will respond when asked for user by loadUserByUserId") {
            val cromUser = createUser()
            whenever(userManager.findUserDetails(any<UUID>())).thenReturn(cromUser)

            val loadedUser = uds.loadUserByUserId(cromUser.userId.toString())
            assertNotNull(loadedUser)
            assertEquals(cromUser.userId.toString(), loadedUser.userId)
        }

        it("will respond when asked for user by loadUserByUsername") {
            val cromUser = createUser()
            whenever(userManager.findUserDetails(any<String>())).thenReturn(cromUser)

            val loadedUser = uds.loadUserByUsername(cromUser.userName)
            assertNotNull(loadedUser)
            assertEquals(cromUser.userName, loadedUser.username)
        }
    }
})
