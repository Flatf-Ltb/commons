package io.flatf.common.lang;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class Predicates {

    private Predicates() {
    }

    /**
     * @param value int
     * @param min   int
     * @return boolean
     */
    public static boolean isGreaterThan(int value, int min) {
        return value > min;
    }

    /**
     * @param value long
     * @param min   long
     * @return boolean
     */
    public static boolean isGreaterThan(long value, long min) {
        return value > min;
    }


    /**
     * @param value int
     * @param min   int
     * @return boolean
     */
    public static boolean isGreaterOrEqualThan(int value, int min) {
        return value >= min;
    }

    /**
     * @param value long
     * @param min   long
     * @return boolean
     */
    public static boolean isGreaterOrEqualThan(long value, long min) {
        return value >= min;
    }

    /**
     * @param value int
     * @param max   int
     * @return boolean
     */
    public static boolean isLessThan(int value, int max) {
        return value < max;
    }

    /**
     * @param value long
     * @param max   long
     * @return boolean
     */
    public static boolean isLessThan(long value, long max) {
        return value < max;
    }

    /**
     * @param value int
     * @param max   int
     * @return boolean
     */
    public static boolean isLessOrEqualThan(int value, int max) {
        return value <= max;
    }

    /**
     * @param value long
     * @param max   long
     * @return boolean
     */
    public static boolean isLessOrEqualThan(long value, long max) {
        return value <= max;
    }

    /**
     * @param value int
     * @param min   int
     * @param max   int
     * @return boolean
     */
    public static boolean isAtWithinRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * @param l   long
     * @param min long
     * @param max long
     * @return boolean
     */
    public static boolean isAtWithinRange(long l, long min, long max) {
        return (l >= min && l <= max);
    }

    /**
     * @param <E>            Collection element type
     * @param collection     Collection<E>
     * @param requiredLength int
     * @return boolean
     */
    public static <E> boolean isRequiredLength(Collection<E> collection, int requiredLength) {
        return collection != null && collection.size() >= requiredLength;
    }

    /**
     * @param <E>            List element type
     * @param list           List<E>
     * @param requiredLength int
     * @return boolean
     */
    public static <E> boolean isRequiredLength(List<E> list, int requiredLength) {
        return list != null && list.size() >= requiredLength;
    }


    /**
     * @param <K>            Map key type
     * @param <V>            Map value type
     * @param map            List<E>
     * @param requiredLength int
     * @return boolean
     */
    public static <K, V> boolean isRequiredLength(Map<K, V> map, int requiredLength) {
        return map != null && map.size() >= requiredLength;
    }

    /**
     * @param <T>            array element type
     * @param array          T[]
     * @param requiredLength int
     * @return boolean
     */
    public static <T> boolean isRequiredLength(T[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          boolean[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(boolean[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          byte[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(byte[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          char[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(char[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          int[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(int[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          long[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(long[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          float[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(float[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param array          double[]
     * @param requiredLength int
     * @return boolean
     */
    public static boolean isRequiredLength(double[] array, int requiredLength) {
        return array != null && array.length >= requiredLength;
    }

    /**
     * @param <T>       T
     * @param param     T
     * @param predicate Predicate<T>
     * @return boolean
     */
    public static <T> boolean isValid(T param, Predicate<T> predicate) {
        return predicate.test(param);
    }

}
