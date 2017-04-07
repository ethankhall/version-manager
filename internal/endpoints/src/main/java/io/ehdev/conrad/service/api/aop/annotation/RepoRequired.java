package io.ehdev.conrad.service.api.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepoRequired {
    /**
     * When true, the repo must exist, when false it must not exist
     */
    boolean exists() default true;
}
