package io.ehdev.conrad.service.api.service.annotation;

import tech.crom.model.security.authorization.CromPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalLink {
    CromPermission permissions() default CromPermission.NONE;
    String name();
    String ref();
}
