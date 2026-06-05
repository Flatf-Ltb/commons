package io.flatf.common.reflect;

import io.flatf.common.collections.MutableSets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public final class AnnotationScanner {

    private AnnotationScanner() {
    }

    public static <A extends Annotation> ImmutableSet<Method> scanPackage(
            @Nonnull Class<A> annotation, @Nonnull Package... packages) {
        MutableSet<Method> methods = MutableSets.newUnifiedSet();
        for (Package scanPackage : packages) {
            if (scanPackage != null) {
                Reflections reflections = new Reflections(new ConfigurationBuilder()
                        .forPackage(scanPackage.getName())
                        .setScanners(Scanners.MethodsAnnotated));
                methods.addAll(reflections.getMethodsAnnotatedWith(annotation));
            }
        }
        return methods.toImmutable();
    }


}
