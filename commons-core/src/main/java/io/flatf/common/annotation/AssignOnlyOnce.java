package io.flatf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author yellow013
 */
@Documented
@Target(FIELD)
@Retention(SOURCE)
public @interface AssignOnlyOnce {
}
