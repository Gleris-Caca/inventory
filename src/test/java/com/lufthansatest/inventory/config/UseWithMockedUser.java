package com.lufthansatest.inventory.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = UserSecurityContext.class)
public @interface UseWithMockedUser {
    String username() default "U001";
    String[] roles() default {"READ_GLOBAL_DATA"};
}
