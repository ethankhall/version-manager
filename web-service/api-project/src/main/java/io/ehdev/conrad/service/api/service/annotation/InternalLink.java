package io.ehdev.conrad.service.api.service.annotation;

import io.ehdev.conrad.database.model.user.ApiUserPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InternalLink {
    ApiUserPermission permissions() default ApiUserPermission.NONE;
    String name();
    String ref();
}
