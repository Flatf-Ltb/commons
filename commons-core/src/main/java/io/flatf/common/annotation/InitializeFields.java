package io.flatf.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yellow013
 */
@Documented
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface InitializeFields {

    Class<?>[] value();

}
