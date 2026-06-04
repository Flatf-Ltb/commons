package io.flatf.common.reflect;

import io.flatf.common.collections.MutableMaps;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static io.flatf.common.collections.ImmutableSets.newImmutableSet;

/**
 * 加载 Annotation 反射信息
 *
 * @author yellow013
 */
public final class AmqpProxyReflection {

    private static final AmqpProxyReflection INSTANCE = new AmqpProxyReflection();

    private AmqpProxyReflection() {
    }

    public static AmqpProxyReflection getInstance() {
        return INSTANCE;
    }

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);

    @SafeVarargs
    public final <A extends Annotation> void initialize(String scanPackage, Class<A>... annotations) {
        if (isInitialized.compareAndSet(false, true)) {
            MutableMap<Class<? extends Annotation>, ImmutableSet<Method>> tempMap = MutableMaps.newUnifiedMap();
            for (Class<A> annotation : annotations) {
                tempMap.put(annotation, scanPackage(scanPackage, annotation));
            }
            this.annotationMethodMap = tempMap.toImmutable();
        } else {
            throw new IllegalStateException("AmqpProxyReflection is not init");
        }
    }

    private ImmutableMap<Class<? extends Annotation>, ImmutableSet<Method>> annotationMethodMap;

    /**
     * @param <A>         Annotation type
     * @param scanPackage String
     * @param annotation  Class<A>
     * @return ImmutableSet<Method>
     */
    public <A extends Annotation> ImmutableSet<Method> scanPackage(String scanPackage, Class<A> annotation) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(scanPackage))
                .setScanners(Scanners.SubTypes, Scanners.MethodsAnnotated,
                        Scanners.MethodsParameter, Scanners.TypesAnnotated));
        ImmutableSet<Method> methods = newImmutableSet(reflections.getMethodsAnnotatedWith(annotation));
        methods.each(this::assertionProxyMethod);
        return methods;
    }

    /**
     * 验证方法返回值是否为void
     *
     * @param method Method
     */
    private void assertionProxyMethod(Method method) {
        if (MethodUtil.notVoidMethod(method))
            throw new IllegalArgumentException("method [" + method + "] return type is not void");
    }

    /**
     * @param proxyClass Class<A>
     * @return ImmutableSet<Method>
     */
    public <A extends Annotation> ImmutableSet<Method> getImplMethods(Class<A> proxyClass) {
        if (isInitialized.get())
            return annotationMethodMap.get(proxyClass);
        throw new IllegalStateException("AmqpProxyReflection is not init");
    }

}
