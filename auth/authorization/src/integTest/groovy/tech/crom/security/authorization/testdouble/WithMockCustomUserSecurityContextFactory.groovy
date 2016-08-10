package tech.crom.security.authorization.testdouble

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.user.CromUser

class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCromUser> {

    @Override
    SecurityContext createSecurityContext(WithMockCromUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        def authentication = new CromUserAuthentication(new CromUser(UUID.randomUUID(), annotation.username(), annotation.displayName()))

        context.setAuthentication(authentication)
        return context
    }
}
