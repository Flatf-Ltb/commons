/*
 * Copyright (C) 2012 Cloudhopper by Twitter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.flatf.common.util;

import io.flatf.common.lang.Validator;

import javax.annotation.Nonnull;

/**
 * Utility class for encoding and decoding objects to a hex string.
 */
public final class HexUtil {

    private static final char[] HEX_TABLE =
            // 16进制Table
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private HexUtil() {
    }

    /**
     * @param bytes byte[]
     * @return String
     */
    public static String toHex(byte[] bytes) {
        if (bytes == null)
            return "";
        return toHex(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return String
     */
    public static String toHex(byte[] bytes, int offset, int length) {
        if (bytes == null)
            return "";
        assertOffsetAndLength(offset, length, bytes.length);
        // each byte is 2 chars in string
        StringBuilder builder = new StringBuilder(length * 2 + 2).append("0x");
        appendHex(builder, bytes, offset, length);
        return builder.toString();
    }

    private static final String BUILDER_VARIABLE_NAME = "builder";

    /**
     * @param builder StringBuilder
     * @param bytes   byte[]
     * @param offset  int
     * @param length  int
     */
    public static void appendHex(@Nonnull StringBuilder builder, byte[] bytes, int offset, int length) {
        Validator.nonNull(builder, BUILDER_VARIABLE_NAME);
        if (bytes == null)
            return;
        assertOffsetAndLength(offset, length, bytes.length);
        int end = offset + length;
        for (int i = offset; i < end; i++) {
            builder.append(HEX_TABLE[(bytes[i] & 0xF0) >>> 4]).append(HEX_TABLE[(bytes[i] & 0x0F)]);
        }
    }

    /**
     * @param builder StringBuilder
     * @param bytes   byte[]
     */
    public static void appendHex(@Nonnull StringBuilder builder, byte[] bytes) {
        Validator.nonNull(builder, BUILDER_VARIABLE_NAME);
        if (bytes == null)
            return;
        appendHex(builder, bytes, 0, bytes.length);
    }

    /**
     * @param value byte
     * @return String
     */
    public static String toHex(byte value) {
        StringBuilder builder = new StringBuilder(4).append("0x");
        appendHex(builder, value);
        return builder.toString();
    }

    /**
     * @param builder StringBuilder
     * @param value   byte
     */
    public static void appendHex(@Nonnull StringBuilder builder, byte value) {
        Validator.nonNull(builder, BUILDER_VARIABLE_NAME);
        builder.append(HEX_TABLE[(value & 0xF0) >>> 4]).append(HEX_TABLE[(value & 0x0F)]);
    }

    /**
     * @param value short
     * @return String
     */
    public static String toHex(short value) {
        StringBuilder builder = new StringBuilder(6).append("0x");
        appendHex(builder, value);
        return builder.toString();
    }

    /**
     * @param builder StringBuilder
     * @param value   short
     */
    public static void appendHex(@Nonnull StringBuilder builder, short value) {
        Validator.nonNull(builder, BUILDER_VARIABLE_NAME);
        builder.append(HEX_TABLE[(value & 0xF000) >>> 12]).append(HEX_TABLE[(value & 0x0F00) >>> 8])
                .append(HEX_TABLE[(value & 0x00F0) >>> 4]).append(HEX_TABLE[(value & 0x000F)]);
    }

    /**
     * @param value int
     * @return String
     */
    public static String toHex(int value) {
        StringBuilder builder = new StringBuilder(10).append("0x");
        appendHex(builder, value);
        return builder.toString();
    }

    /**
     * @param builder StringBuilder
     * @param value   int
     */
    public static void appendHex(@Nonnull StringBuilder builder, int value) {
        Validator.nonNull(builder, BUILDER_VARIABLE_NAME);
        builder.append(HEX_TABLE[(value & 0xF000_0000) >>> 28])
                .append(HEX_TABLE[(value & 0x0F00_0000) >>> 24])
                .append(HEX_TABLE[(value & 0x00F0_0000) >>> 20])
                .append(HEX_TABLE[(value & 0x000F_0000) >>> 16])
                .append(HEX_TABLE[(value & 0x0000_F000) >>> 12])
                .append(HEX_TABLE[(value & 0x0000_0F00) >>> 8])
                .append(HEX_TABLE[(value & 0x0000_00F0) >>> 4])
                .append(HEX_TABLE[(value & 0x0000_000F)]);
    }

    /**
     * @param value long
     * @return String
     */
    public static String toHex(long value) {
        StringBuilder builder = new StringBuilder(18).append("0x");
        appendHex(builder, value);
        return builder.toString();
    }

    /**
     * @param builder StringBuilder
     * @param value   long
     */
    public static void appendHex(@Nonnull StringBuilder builder, long value) {
        appendHex(builder, (int) ((value & 0xFFFF_FFFF_0000_0000L) >>> 32));
        appendHex(builder, (int) (value & 0x0000_0000_FFFF_FFFFL));
    }

    /**
     * @param offset      int
     * @param length      int
     * @param arrayLength int
     */
    private static void assertOffsetAndLength(int offset, int length, int arrayLength) {
        if (offset < 0)
            throw new IllegalArgumentException("The array offset was negative");
        if (length < 0)
            throw new IllegalArgumentException("The array length was negative");
        if (offset + length > arrayLength)
            throw new ArrayIndexOutOfBoundsException("The array offset+length would access past end of array");
    }

    /**
     * @param c char
     * @return int
     */
    public static int hexCharToInt(char c) {
        return switch (c) {
            case '0' -> 0;
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            case '9' -> 9;
            case 'A', 'a' -> 10;
            case 'B', 'b' -> 11;
            case 'C', 'c' -> 12;
            case 'D', 'd' -> 13;
            case 'E', 'e' -> 14;
            case 'F', 'f' -> 15;
            default -> throw new IllegalArgumentException(
                    "The character [" + c + "] does not represent a valid hex digit");
        };
    }

    /**
     * @param hex CharSequence
     * @return byte[]
     */
    public static byte[] toByteArray(CharSequence hex) {
        if (hex == null)
            return new byte[0];
        return toByteArray(hex, 0, hex.length());
    }

    /**
     * Creates a byte array from a CharSequence (String, StringBuilder, etc.)
     * containing only valid hexadecimal-formatted characters. Each grouping of 2
     * characters represents a byte in "Big Endian" format. The hex CharSequence must
     * be an even length of characters. For example, a String of "1234" would return
     * the byte array { 0x12, 0x34 }.
     *
     * @param hex    The String, StringBuilder, etc. that contains the sequence of
     *               hexadecimal character values.
     * @param offset The offset within the sequence to start from. If the offset is
     *               invalid, it will cause an IllegalArgumentException.
     * @param length The length from the offset to convert. If the length is
     *               invalid, it will cause an IllegalArgumentException.
     * @return A new byte array representing the sequence of bytes created from the
     * sequence of hexadecimal characters. If the hexString is null, then
     * this method will return null.
     */
    public static byte[] toByteArray(CharSequence hex, int offset, int length) {
        if (hex == null)
            return new byte[0];
        assertOffsetAndLength(offset, length, hex.length());

        // a hex string must be in increments of 2
        if ((length % 2) != 0)
            throw new IllegalArgumentException(
                    "The hex string did not contain an even number of characters [actual=" + length + "]");

        // convert hex string to a byte array
        byte[] bytes = new byte[length / 2];

        int j = 0;
        int end = offset + length;

        for (int i = offset; i < end; i += 2) {
            int highNibble = hexCharToInt(hex.charAt(i));
            int lowNibble = hexCharToInt(hex.charAt(i + 1));
            bytes[j++] = (byte) (((highNibble << 4) & 0xF0) | (lowNibble & 0x0F));
        }
        return bytes;
    }

}
