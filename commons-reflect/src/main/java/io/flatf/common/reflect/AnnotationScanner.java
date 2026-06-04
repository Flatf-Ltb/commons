package io.flatf.common.reflect;

import org.eclipse.collections.api.set.ImmutableSet;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationScanner {

    private AnnotationScanner() {
    }

    public static <A extends Annotation> ImmutableSet<Method> scanPackage(
            @Nonnull Class<A> annotation, @Nonnull Package... packages) {
        return null;
    }


}
