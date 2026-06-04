package io.flatf.common.util;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.io.Serial;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author yellow013
 */
public final class JreReflection {

    private JreReflection() {
    }

    /**
     * @param <R>       R type
     * @param <T>       T type
     * @param clazz     Class<T>
     * @param fieldName String
     * @return R object
     * @throws RuntimeReflectionException rre
     */
    public static <R, T> R extractField(Class<T> clazz, String fieldName) throws RuntimeReflectionException {
        return extractField(clazz, null, fieldName);
    }

    /**
     * @param <R>       return Type
     * @param <T>       Class Type
     * @param clazz     Class<T>
     * @param obj       T
     * @param fieldName String
     * @return R
     */
    public static <R, T> R extractField(Class<T> clazz, T obj, String fieldName)
            throws RuntimeReflectionException {
        try {
            Field field = getField(clazz, fieldName);
            field.setAccessible(true);
            //noinspection unchecked
            return (R) field.get(obj);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeReflectionException(
                    "Can not access field: [" + fieldName + "] be caused by -> "
                            + e.getMessage(), e);
        }
    }

    /**
     * @param clazz     Class<T>
     * @param fieldName String
     * @param <T>       Class type
     * @return Field
     */
    public static <T> Field getField(Class<T> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass == null)
                throw new RuntimeReflectionException("Can not find field: [" + fieldName + "]", e);
            else
                return getField(superClass, fieldName);
        }
    }

    /**
     * @param <T>  Class Type
     * @param type Class<T>
     * @return T
     */
    public static <T> T invokeConstructor(Class<T> type) throws RuntimeReflectionException {
        return invokeConstructor(type, new Object[]{});
    }

    /**
     * @param <T>  Class Type
     * @param type Class<T>
     * @param args Object[]
     * @return T
     */
    public static <T> T invokeConstructor(Class<T> type, Object... args) throws RuntimeReflectionException {
        try {
            return ConstructorUtils.invokeConstructor(type, args);
        } catch (NoSuchMethodException
                 | IllegalAccessException
                 | InvocationTargetException
                 | InstantiationException e) {
            throw new RuntimeReflectionException(
                    "Can not invoke constructor with class [" + type.getName() + "] be caused by -> " + e.getMessage(), e);
        }
    }

    /**
     * @author yellow013
     */
    public static class RuntimeReflectionException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -8452094826323264342L;

        public RuntimeReflectionException(String msg, Throwable cause) {
            super(msg, cause);
        }

    }

}
