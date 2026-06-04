package io.flatf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 标识函数的返回值需要被检查
 *
 * @author yellow013
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface CheckReturnValue {
}
