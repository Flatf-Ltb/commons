package io.flatf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * 抽象函数标识
 *
 * @author yellow013
 */
@Documented
@Target(METHOD)
@Retention(SOURCE)
public @interface AbstractFunction {
}
