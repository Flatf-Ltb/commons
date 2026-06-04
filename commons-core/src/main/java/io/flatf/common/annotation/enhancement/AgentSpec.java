package io.flatf.common.annotation.enhancement;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Target({METHOD, TYPE})
public @interface AgentSpec {

    Class<?> superClass() default Object.class;

    String prompt() default "";

    String spec() default "";

}
