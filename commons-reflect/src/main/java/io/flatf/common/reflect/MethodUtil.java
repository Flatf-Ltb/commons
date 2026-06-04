package io.flatf.common.reflect;

import java.lang.reflect.Method;

public final class MethodUtil {

    private MethodUtil() {}

    public static boolean isVoidMethod(Method method) {
        return method.getReturnType() == Void.TYPE;
    }

    public static boolean notVoidMethod(Method method) {
        return method.getReturnType() != Void.TYPE;
    }


}
