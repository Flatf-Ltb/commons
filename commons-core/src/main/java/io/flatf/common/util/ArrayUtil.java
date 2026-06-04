package io.flatf.common.util;

import io.flatf.common.lang.Validator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.ceil;
import static java.lang.Math.min;
import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOfRange;
import static java.util.stream.IntStream.range;

public final class ArrayUtil {

    private ArrayUtil() {
    }

    /**
     * @param length int
     * @return boolean
     */
    public static boolean illegalLength(int length) {
        return length >= 1;
    }

    /**
     * @param booleans boolean[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(boolean[] booleans) {
        return booleans == null || booleans.length == 0;
    }

    /**
     * @param bytes byte[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * @param chars char[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(char[] chars) {
        return chars == null || chars.length == 0;
    }

    /**
     * @param ints int[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(int[] ints) {
        return ints == null || ints.length == 0;
    }

    /**
     * @param longs long[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(long[] longs) {
        return longs == null || longs.length == 0;
    }

    /**
     * @param floats float[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(float[] floats) {
        return floats == null || floats.length == 0;
    }

    /**
     * @param doubles double[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(double[] doubles) {
        return doubles == null || doubles.length == 0;
    }

    /**
     * @param array String[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(String[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @param array Object[]
     * @return boolean
     */
    public static boolean isNullOrEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * @param origin boolean[]
     * @return boolean[]
     */
    public static boolean[] newOf(@Nonnull boolean[] origin) {
        boolean[] target = new boolean[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin byte[]
     * @return byte[]
     */
    public static byte[] newOf(@Nonnull byte[] origin) {
        byte[] target = new byte[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin char[]
     * @return char[]
     */
    public static char[] newOf(@Nonnull char[] origin) {
        char[] target = new char[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin int[]
     * @return int[]
     */
    public static int[] newOf(@Nonnull int[] origin) {
        int[] target = new int[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin long[]
     * @return long[]
     */
    public static long[] newOf(@Nonnull long[] origin) {
        long[] target = new long[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin float[]
     * @return float[]
     */
    public static float[] newOf(@Nonnull float[] origin) {
        float[] target = new float[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin double[]
     * @return double[]
     */
    public static double[] newOf(@Nonnull double[] origin) {
        double[] target = new double[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin String[]
     * @return String[]
     */
    public static String[] newOf(@Nonnull String[] origin) {
        String[] target = new String[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    /**
     * @param origin Object[]
     * @return Object[]
     */
    public static Object[] newOf(@Nonnull Object[] origin) {
        Object[] target = new Object[origin.length];
        arraycopy(origin, 0, target, 0, origin.length);
        return target;
    }

    private static final String COPY_ORIGIN = "origin";

    /**
     * @param origin boolean[]
     * @param target boolean[]
     * @return boolean[]
     */
    public static boolean[] copy(@Nonnull boolean[] origin, @Nullable boolean[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new boolean[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin byte[]
     * @param target byte[]
     * @return byte[]
     */
    public static byte[] copy(@Nonnull byte[] origin, @Nullable byte[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new byte[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin char[]
     * @param target char[]
     * @return char[]
     */
    public static char[] copy(@Nonnull char[] origin, @Nullable char[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new char[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin int[]
     * @param target int[]
     * @return int[]
     */
    public static int[] copy(@Nonnull int[] origin, @Nullable int[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new int[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin long[]
     * @param target long[]
     * @return long[]
     */
    public static long[] copy(@Nonnull long[] origin, @Nullable long[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new long[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin float[]
     * @param target float[]
     * @return float[]
     */
    public static float[] copy(@Nonnull float[] origin, @Nullable float[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new float[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin double[]
     * @param target double[]
     * @return double[]
     */
    public static double[] copy(@Nonnull double[] origin, @Nullable double[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new double[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin String[]
     * @param target String[]
     * @return String[]
     */
    public static String[] copy(@Nonnull String[] origin, @Nullable String[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new String[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }

    /**
     * @param origin Object[]
     * @param target Object[]
     * @return Object[]
     */
    public static Object[] copy(@Nonnull Object[] origin, @Nullable Object[] target) {
        Validator.requiredLength(origin, 1, COPY_ORIGIN);
        if (target == null)
            target = new Object[origin.length];
        arraycopy(origin, 0, target, 0, min(target.length, origin.length));
        return target;
    }


    @SafeVarargs
    public static <T> List<T[]> splitArray(int chunkSize, T... original) {
        int numOfChunks = (int) ceil((double) original.length / chunkSize);
        return range(0, numOfChunks)
                .mapToObj(i -> copyOfRange(original,
                        //from
                        i * chunkSize,
                        //to
                        min((i + 1) * chunkSize, original.length)
                ))
                .toList();
    }

    public static List<byte[]> splitArray(int chunkSize, byte... original) {
        int numOfChunks = (int) ceil((double) original.length / chunkSize);
        return range(0, numOfChunks)
                .mapToObj(i -> copyOfRange(original,
                        //from
                        i * chunkSize,
                        //to
                        min((i + 1) * chunkSize, original.length)
                ))
                .toList();
    }

    public static List<int[]> splitArray(int chunkSize, int... original) {
        int numOfChunks = (int) ceil((double) original.length / chunkSize);
        return range(0, numOfChunks)
                .mapToObj(i -> copyOfRange(original,
                        //from
                        i * chunkSize,
                        //to
                        min((i + 1) * chunkSize, original.length)
                ))
                .toList();
    }

    public static List<long[]> splitArray(int chunkSize, long... original) {
        int numOfChunks = (int) ceil((double) original.length / chunkSize);
        return range(0, numOfChunks)
                .mapToObj(i -> copyOfRange(original,
                        //from
                        i * chunkSize,
                        //to
                        min((i + 1) * chunkSize, original.length)
                ))
                .toList();
    }

    public static List<double[]> splitArray(int chunkSize, double... original) {
        int numOfChunks = (int) ceil((double) original.length / chunkSize);
        return range(0, numOfChunks)
                .mapToObj(i -> copyOfRange(original,
                        //from
                        i * chunkSize,
                        //to
                        min((i + 1) * chunkSize, original.length)
                ))
                .toList();
    }

    public static int[] shiftLeft(final int[] array) {
        Arrays.sort(array);
        return array;
    }

}
