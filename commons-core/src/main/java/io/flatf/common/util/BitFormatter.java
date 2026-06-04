package io.flatf.common.util;

import static java.lang.Integer.toBinaryString;
import static java.lang.Long.toBinaryString;
import static java.lang.Math.max;

public final class BitFormatter {

    private BitFormatter() {
    }

    /**
     * 将[byte]转换为二进制输出
     *
     * @param b byte
     * @return String
     */
    public static String byteBinary(byte b) {
        String binary = toBinaryString(b);
        return highPosFill(Byte.SIZE - binary.length(), binary);
    }

    /**
     * 将[char]转换为二进制输出, 高位补0
     *
     * @param c char
     * @return String
     */
    public static String charBinary(char c) {
        String binary = toBinaryString(c);
        return highPosFill(Character.SIZE - binary.length(), binary);
    }

    /**
     * 将[char]转换为二进制输出并格式化, 高位补0
     *
     * @param c char
     * @return String
     */
    public static String charBinaryFormat(char c) {
        return new StringBuilder(17).append(charBinary(c)).insert(8, ' ').toString();
    }

    /**
     * 将[short]转换为二进制输出, 高位补0
     *
     * @param s short
     * @return String
     */
    public static String shortBinary(short s) {
        String binary = toBinaryString(s);
        return highPosFill(Short.SIZE - binary.length(), binary);
    }

    /**
     * 将[short]转换为二进制输出并格式化, 高位补0
     *
     * @param s short
     * @return String
     */
    public static String shortBinaryFormat(short s) {
        return new StringBuilder(17).append(shortBinary(s))
                .insert(8, ' ').toString();
    }

    /**
     * 将[int]转换为二进制输出, 高位补0
     *
     * @param i int
     * @return String
     */
    public static String intBinary(int i) {
        String binary = toBinaryString(i);
        return highPosFill(Integer.SIZE - binary.length(), binary);
    }

    /**
     * 将[int]转换为二进制输出并格式化, 高位补0
     *
     * @param i int
     * @return String
     */
    public static String intBinaryFormat(int i) {
        return new StringBuilder(35).append(intBinary(i))
                .insert(24, ' ').insert(16, ' ')
                .insert(8, ' ').toString();
    }

    /**
     * 将[long]转换为二进制输出, 高位补0
     *
     * @param l long
     * @return String
     */
    public static String longBinary(long l) {
        String binary = toBinaryString(l);
        return highPosFill(Long.SIZE - binary.length(), binary);
    }

    /**
     * 将[long]转换为二进制输出并格式化, 高位补0
     *
     * @param l long
     * @return String
     */
    public static String longBinaryFormat(long l) {
        return new StringBuilder(71).append(longBinary(l))
                .insert(56, ' ').insert(48, ' ')
                .insert(40, ' ').insert(32, ' ')
                .insert(24, ' ').insert(16, ' ')
                .insert(8, ' ').toString();
    }

    /**
     * 空白长度, 实际值, 返回的字符串填充指定长度的0
     *
     * @param blankLen  int
     * @param binaryStr String
     * @return String
     */
    private static String highPosFill(int blankLen, String binaryStr) {
        return "0".repeat(max(0, blankLen)) + binaryStr;
    }

}
