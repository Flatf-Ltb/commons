package io.flatf.common.number;

import static java.lang.StrictMath.IEEEremainder;

public final class DoubleArithmetic {

    /**
     *
     */
    private final static long PRECISION_8_MULTIPLIER = 10_000_000_000L;

    /**
     *
     */
    private final static double PRECISION_8_DIVISOR = 1_000_000_000D;

    /**
     *
     */
    private final static long PRECISION_4_MULTIPLIER = 1_000_000L;

    /**
     *
     */
    private final static double PRECISION_4_DIVISOR = 100_000D;

    /**
     *
     */
    private final static long CORRECTION_FACTOR = 1L;

    /**
     *
     */
    private final static long CORRECTION_DIVISOR = 10L;

    /**
     * 保留小数点后4位的精度
     *
     * @param d1 double
     * @return long
     */
    private static long doubleToLong4(double d1) {
        return ((long) (d1 * PRECISION_4_MULTIPLIER) + CORRECTION_FACTOR) / CORRECTION_DIVISOR;
    }

    /**
     * 小数点左移4位
     *
     * @param l1 long
     * @return double
     */
    private static double longToDouble4(long l1) {
        return l1 / PRECISION_4_DIVISOR;
    }

    /**
     * 保留小数点后8位的精度
     *
     * @param d1 double
     * @return long
     */
    private static long doubleToLong8(double d1) {
        return ((long) (d1 * PRECISION_8_MULTIPLIER) + CORRECTION_FACTOR) / CORRECTION_DIVISOR;
    }

    /**
     * 小数点左移8位
     *
     * @param l1 long
     * @return double
     */
    private static double longToDouble8(long l1) {
        return l1 / PRECISION_8_DIVISOR;
    }

    /**
     * 修正double保留4位精度
     *
     * @param d1 double
     * @return double
     */
    public static double correction4(double d1) {
        return longToDouble4(doubleToLong4(d1));
    }

    /**
     * 修正double保留8位精度
     *
     * @param d1 double
     * @return double
     */
    public static double correction8(double d1) {
        return longToDouble8(doubleToLong8(d1));
    }

    /**
     * 4位精度相加
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double add4(double d1, double d2) {
        return correction4(d1 + d2);
    }

    /**
     * 8位精度相加
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double add8(double d1, double d2) {
        return correction8(d1 + d2);
    }

    /**
     * 8位精度相减
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double subtraction(double d1, double d2) {
        return correction8(d1 - d2);
    }

    /**
     * 4位精度乘法
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double multiply4(double d1, double d2) {
        return correction4(d1 * d2);
    }

    /**
     * 8位精度乘法
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double multiply8(double d1, double d2) {
        return correction8(d1 * d2);
    }

    /**
     * 8位精度除法
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double div(double d1, double d2) {
        return correction8(d1 / d2);
    }

    /**
     * 8位精度取余
     *
     * @param d1 double
     * @param d2 double
     * @return double
     */
    public static double remainder(double d1, double d2) {
        return IEEEremainder(d1, d2);
    }

    public static void main(String[] args) {
        System.out.println(add8(0.5321, 1.1111123234));
        System.out.println(0.5321 + 1.1111123234);
        int i = 0;
        for (double d = 0.0001; d < 0.01; d += 0.00001) {
            System.out.println(i);
            System.out.println(correction8(d));
            i++;
        }
    }

}
