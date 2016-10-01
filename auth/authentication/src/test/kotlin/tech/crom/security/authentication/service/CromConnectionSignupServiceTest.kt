package tech.crom.security.authentication.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it
import org.mockito.internal.verification.VerificationModeFactory
import org.springframework.social.connect.Connection
import org.springframework.social.connect.UserProfile
import tech.crom.database.api.UserManager
import tech.crom.security.authentication.createUser
import kotlin.test.assertEquals

class CromConnectionSignupServiceTest: Spek({
    context("registering user at signup") {
        val userManager: UserManager = mock()
        val connection: Connection<*> = mock()

        val signupService = CromConnectionSignupService(userManager)

        it("will try to create new user") {
            val userProfile: UserProfile = mock()
            val user = createUser()

            whenever(userProfile.name).thenReturn("name")
            whenever(userManager.userNameExists(any())).thenReturn(true, true, false)
            whenever(connection.fetchUserProfile()).thenReturn(userProfile)
            whenever(userManager.createUser(any(), any())).thenReturn(user)

            assertEquals(signupService.execute(connection), user.userUid.toString())

            verify(userManager, VerificationModeFactory.times(3)).userNameExists(any())
        }
    }
})
