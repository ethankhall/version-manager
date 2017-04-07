package tech.crom.security.authorization.testdouble

import org.springframework.security.test.context.support.WithSecurityContext

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCromUser {

    String username() default "username";

    String displayName() default "displayName";
}
