package io.flatf.common.number;

/**
 * @author yellow013
 */
public final class DecimalSupporter {

    public static final long LONG_MULTIPLIER_1L = 1L;

    public static final double DOUBLE_MULTIPLIER_1D = 1.0d;

    public static final long LONG_MULTIPLIER_10L = 10L;

    public static final double DOUBLE_MULTIPLIER_10D = 10.0d;

    public static final long LONG_MULTIPLIER_100L = 100L;

    public static final double DOUBLE_MULTIPLIER_100D = 100.0d;

    public static final long LONG_MULTIPLIER_1000L = 1000L;

    public static final double DOUBLE_MULTIPLIER_1000D = 1000.0d;

    public static final long LONG_MULTIPLIER_10000L = 10000L;

    public static final double DOUBLE_MULTIPLIER_10000D = 10000.0d;

    public static final long LONG_MULTIPLIER_100000L = 100000L;

    public static final double DOUBLE_MULTIPLIER_100000D = 100000.0d;

    public static final long LONG_MULTIPLIER_1000000L = 1000000L;

    public static final double DOUBLE_MULTIPLIER_1000000D = 1000000.0d;

    public static final long LONG_MULTIPLIER_10000000L = 10000000L;

    public static final double DOUBLE_MULTIPLIER_10000000D = 10000000.0d;

    public static final long LONG_MULTIPLIER_100000000L = 100000000L;

    public static final double DOUBLE_MULTIPLIER_100000000D = 100000000.0d;

    /**
     * @param d double
     * @return long
     */
    public static long doubleToLong2(double d) {
        return (long) (d * LONG_MULTIPLIER_100L);
    }

    /**
     * @param l long
     * @return double
     */
    public static double longToDouble2(long l) {
        return l / DOUBLE_MULTIPLIER_100D;
    }

    /**
     * @param d double
     * @return long
     */
    public static long doubleToLong4(double d) {
        return (long) (d * LONG_MULTIPLIER_10000L);
    }

    /**
     * @param l long
     * @return double
     */
    public static double longToDouble4(long l) {
        return l / DOUBLE_MULTIPLIER_10000D;
    }

    /**
     * @param d double
     * @return long
     */
    public static long doubleToLong6(double d) {
        return (long) (d * LONG_MULTIPLIER_1000000L);
    }

    /**
     * @param l long
     * @return double
     */
    public static double longToDouble6(long l) {
        return l / DOUBLE_MULTIPLIER_1000000D;
    }

    /**
     * @param d double
     * @return long
     */
    public static long doubleToLong8(double d) {
        return (long) (d * LONG_MULTIPLIER_100000000L);
    }

    /**
     * @param l long
     * @return double
     */
    public static double longToDouble8(long l) {
        return l / DOUBLE_MULTIPLIER_100000000D;
    }

    public static void main(String[] args) {

        System.out.println();
        System.out.println(longToDouble8(doubleToLong8(4.981312)));

    }

}
