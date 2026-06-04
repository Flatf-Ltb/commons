package io.flatf.common.util;

import io.flatf.common.lang.Validator;

import javax.annotation.Nonnull;

import static io.flatf.common.util.BitFormatter.longBinaryFormat;

public final class BitOperator {

    private BitOperator() {
    }


    /**
     * @param bytes byte[]
     * @return char
     * @throws ArrayIndexOutOfBoundsException exception
     */
    public static char bytesToChar(byte[] bytes) throws ArrayIndexOutOfBoundsException {
        Validator.requiredLength(bytes, 2, "bytes array");
        return (char) (((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF));
    }


    /**
     * @param bytes  byte[]
     * @param offset int
     * @return char
     * @throws ArrayIndexOutOfBoundsException exception
     */
    public static char bytesToChar(byte[] bytes, int offset) throws ArrayIndexOutOfBoundsException {
        if (bytes == null || bytes.length < offset + 2)
            throw new ArrayIndexOutOfBoundsException("byte array length must be greater than [offset + 2]");
        return (char) (((bytes[offset] & 0xFF) << 8) | (bytes[offset + 1] & 0xFF));
    }


    /**
     * @param bytes byte[]
     * @return int
     * @throws ArrayIndexOutOfBoundsException exception
     */
    public static int bytesToInt(@Nonnull byte[] bytes) throws ArrayIndexOutOfBoundsException {
        Validator.requiredLength(bytes, 4, "bytes array");
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }


    /**
     * @param bytes  byte[]
     * @param offset int
     * @return int
     * @throws ArrayIndexOutOfBoundsException exception
     */
    public static int bytesToInt(byte[] bytes, int offset) throws ArrayIndexOutOfBoundsException {
        if (bytes == null || bytes.length < offset + 4)
            throw new ArrayIndexOutOfBoundsException("byte array length must be greater than [offset + 4]");
        return ((bytes[offset] & 0xFF) << 24)
                | ((bytes[offset + 1] & 0xFF) << 16)
                | ((bytes[offset + 2] & 0xFF) << 8)
                | (bytes[offset + 3] & 0xFF);
    }


    /**
     * 两个[char]合并为[int]
     *
     * @param highPos char
     * @param lowPos  char
     * @return int
     */
    public static int merge(char highPos, char lowPos) {
        return (highPos << 16) | lowPos;
    }


    /**
     * 两个<b> [char] </b>合并为<b> [int] </b>
     *
     * @param highPos short
     * @param lowPos  short
     * @return int
     */
    public static int merge(short highPos, short lowPos) {
        return (highPos << 16) | lowPos;
    }


    /**
     * 四个<b> [char] </b>合并为<b> [long] </b>
     *
     * @param highPos char
     * @param second  char
     * @param third   char
     * @param lowPos  char
     * @return long
     */
    public static long merge(char highPos, char second, char third, char lowPos) {
        return (((long) highPos) << 48)
                | ((long) second << 32)
                | ((long) third << 16)
                | lowPos;
    }


    /**
     * 四个<b> [short] </b>合并为<b> [long] </b>
     *
     * @param highPos short
     * @param second  short
     * @param third   short
     * @param lowPos  short
     * @return long
     */
    public static long merge(short highPos, short second, short third, short lowPos) {
        return (((long) highPos) << 48)
                | ((long) second << 32)
                | ((long) third << 16)
                | lowPos;
    }


    /**
     * 两个<b> [int] </b>合并为<b> [long] </b>
     *
     * @param highPos int
     * @param lowPos  int
     * @return long
     */
    public static long merge(int highPos, int lowPos) {
        return (((long) highPos) << 32)
                | lowPos;
    }


    //高位掩码
    public static final long LONG_HIGH_POS_MASK = 0xFFFF_FFFF_0000_0000L;

    /**
     * @param l long
     * @return int
     */
    public static int getLongHighPos(long l) {
        return (int) ((l & LONG_HIGH_POS_MASK) >> 32);
    }

    /*
     * 低位掩码
     */
    public static final long LONG_LOW_POS_MASK = 0x0000_0000_FFFF_FFFFL;

    /**
     * @param l long
     * @return int
     */
    public static int getLongLowPos(long l) {
        return (int) (l & LONG_LOW_POS_MASK);
    }

    /**
     * 奇数
     *
     * @param i int
     * @return boolean
     */
    public static boolean isOdd(int i) {
        return (i & 1) != 0;
    }

    /**
     * 偶数
     *
     * @param i int
     * @return boolean
     */
    public static boolean isEven(int i) {
        return !isOdd(i);
    }

    /**
     * 奇数
     *
     * @param l long
     * @return boolean
     */
    public static boolean isOdd(long l) {
        return (l & 1) != 0;
    }

    /**
     * 偶数
     *
     * @param l long
     * @return boolean
     */
    public static boolean isEven(long l) {
        return !isOdd(l);
    }

    private static final int MAX_CAPACITY = 1 << 30;

    /**
     * 返回最接近参数的2的幂
     *
     * @param i int
     * @return int
     */
    public static int minPow2(int i) {
        int n = i - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAX_CAPACITY) ? MAX_CAPACITY : n + 1;
    }

    /**
     * 获取n位所能表示的最大值
     *
     * @param n int
     * @return long
     */
    public static long maxValueOfBit(int n) {
        return n < 1 || n > 63 ? 0 : ~(-1L << n);
    }

    /**
     * 交换两个变量的值
     *
     * @param x int
     * @param y int
     */
    public static void swap(int x, int y) {
        x ^= y;
        y ^= x;
        x ^= y;
    }

    /**
     * 交换两个变量的值
     *
     * @param x long
     * @param y long
     */
    public static void swap(long x, long y) {
        x ^= y;
        y ^= x;
        x ^= y;
    }


    public static void main(String[] args) {
        System.out.println(minPow2(129));
        int i = 46346;

        System.out.println(longBinaryFormat(-1L));
        System.out.println(longBinaryFormat(-1L << i));
        System.out.println(longBinaryFormat(-1L << Math.abs(i)));
        System.out.println(longBinaryFormat(~(-1L << i)));
        System.out.println(maxValueOfBit(i));

    }

}
