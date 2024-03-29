package tech.crom.security.authorization.testdouble

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory
import tech.crom.model.security.authentication.CromUserAuthentication
import tech.crom.model.user.CromUser
import tech.crom.security.authorization.impl.AuthUtils

class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCromUser> {

    @Override
    SecurityContext createSecurityContext(WithMockCromUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        def authentication = new CromUserAuthentication(new CromUser(AuthUtils.randomLongGenerator(), annotation.username(), annotation.displayName()))

        context.setAuthentication(authentication)
        return context
    }
}
