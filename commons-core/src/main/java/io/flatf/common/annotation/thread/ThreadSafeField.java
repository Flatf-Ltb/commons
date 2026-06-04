package io.flatf.common.annotation.thread;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Documented
@Target({FIELD})
@Retention(CLASS)
public @interface ThreadSafeField {
}
