package io.flatf.common.codec;

import java.nio.ByteBuffer;
import java.text.ParseException;

/**
 * Code originally copied from the OpenDS Java project.
 * <p>
 * <a href="http://www.opends.org/">www.opends.org</a>
 * <p>
 * This class removes the dependency on the Jakarta commons-codec library. Since
 * this utility package is used in every Cloudhopper Java project, providing our
 * own implementation (that's also much faster) allows the removal of a
 * transitive dependency for every Cloudhopper Java project :-)
 * <p>
 * This class provides methods for performing base64 encoding and decoding.
 * Base64 is a mechanism for encoding binary data in ASCII form by converting
 * sets of three bytes with eight significant bits each to sets of four bytes
 * with six significant bits each.
 *
 * @author joelauer (twitter: @jjlauer or
 * <a href="http://twitter.com/jjlauer" target=
 * window>http://twitter.com/jjlauer</a>)
 */
public class Base64Codec {

    /**
     * The set of characters that may be used in base64-encoded values.
     */
    private static final char[] BASE64_ALPHABET = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz" + "0123456789+/").toCharArray();

    /**
     * Prevent instance creation.
     */
    private Base64Codec() {
        // No implementation required.
    }

    /**
     * Encodes the provided raw data using base64.
     *
     * @param rawData The raw data to encode. It must not be <CODE>null</CODE>.
     * @return The base64-encoded representation of the provided raw data.
     */
    public static String encode(byte[] rawData) {
        StringBuilder buf = new StringBuilder(4 * rawData.length / 3);

        int pos = 0;
        int iterations = rawData.length / 3;
        for (int i = 0; i < iterations; i++) {
            int value = ((rawData[pos++] & 0xFF) << 16)
                    | ((rawData[pos++] & 0xFF) << 8)
                    | (rawData[pos++] & 0xFF);

            buf.append(BASE64_ALPHABET[(value >>> 18) & 0x3F]);
            buf.append(BASE64_ALPHABET[(value >>> 12) & 0x3F]);
            buf.append(BASE64_ALPHABET[(value >>> 6) & 0x3F]);
            buf.append(BASE64_ALPHABET[value & 0x3F]);
        }

        switch (rawData.length % 3) {
            case 1 -> {
                buf.append(BASE64_ALPHABET[(rawData[pos] >>> 2) & 0x3F]);
                buf.append(BASE64_ALPHABET[(rawData[pos] << 4) & 0x3F]);
                buf.append("==");
            }
            case 2 -> {
                int value = ((rawData[pos++] & 0xFF) << 8) | (rawData[pos] & 0xFF);
                buf.append(BASE64_ALPHABET[(value >>> 10) & 0x3F]);
                buf.append(BASE64_ALPHABET[(value >>> 4) & 0x3F]);
                buf.append(BASE64_ALPHABET[(value << 2) & 0x3F]);
                buf.append("=");
            }
        }

        return buf.toString();
    }

    /**
     * Decodes the provided set of base64-encoded data.
     *
     * @param encodedData The base64-encoded data to decode. It must not be
     *                    <CODE>null</CODE>.
     * @return The decoded raw data.
     * @throws ParseException If a problem occurs while attempting to decode the
     *                        provided data.
     */
    public static byte[] decode(String encodedData) throws ParseException {
        // The encoded value must have length that is a multiple of four bytes.
        int length = encodedData.length();

        if ((length % 4) != 0)
            // Message message = ERR_BASE64_DECODE_INVALID_LENGTH.get(encodedData);
            throw new ParseException("Base64 data was not 4-byte aligned", 0);

        ByteBuffer buf = ByteBuffer.allocate(length);
        for (int i = 0; i < length; i += 4) {
            boolean append = true;
            int value = 0;

            for (int j = 0; j < 4; j++) {
                switch (encodedData.charAt(i + j)) {
                    case 'A' -> value <<= 6;
                    case 'B' -> value = (value << 6) | 0x01;
                    case 'C' -> value = (value << 6) | 0x02;
                    case 'D' -> value = (value << 6) | 0x03;
                    case 'E' -> value = (value << 6) | 0x04;
                    case 'F' -> value = (value << 6) | 0x05;
                    case 'G' -> value = (value << 6) | 0x06;
                    case 'H' -> value = (value << 6) | 0x07;
                    case 'I' -> value = (value << 6) | 0x08;
                    case 'J' -> value = (value << 6) | 0x09;
                    case 'K' -> value = (value << 6) | 0x0A;
                    case 'L' -> value = (value << 6) | 0x0B;
                    case 'M' -> value = (value << 6) | 0x0C;
                    case 'N' -> value = (value << 6) | 0x0D;
                    case 'O' -> value = (value << 6) | 0x0E;
                    case 'P' -> value = (value << 6) | 0x0F;
                    case 'Q' -> value = (value << 6) | 0x10;
                    case 'R' -> value = (value << 6) | 0x11;
                    case 'S' -> value = (value << 6) | 0x12;
                    case 'T' -> value = (value << 6) | 0x13;
                    case 'U' -> value = (value << 6) | 0x14;
                    case 'V' -> value = (value << 6) | 0x15;
                    case 'W' -> value = (value << 6) | 0x16;
                    case 'X' -> value = (value << 6) | 0x17;
                    case 'Y' -> value = (value << 6) | 0x18;
                    case 'Z' -> value = (value << 6) | 0x19;
                    case 'a' -> value = (value << 6) | 0x1A;
                    case 'b' -> value = (value << 6) | 0x1B;
                    case 'c' -> value = (value << 6) | 0x1C;
                    case 'd' -> value = (value << 6) | 0x1D;
                    case 'e' -> value = (value << 6) | 0x1E;
                    case 'f' -> value = (value << 6) | 0x1F;
                    case 'g' -> value = (value << 6) | 0x20;
                    case 'h' -> value = (value << 6) | 0x21;
                    case 'i' -> value = (value << 6) | 0x22;
                    case 'j' -> value = (value << 6) | 0x23;
                    case 'k' -> value = (value << 6) | 0x24;
                    case 'l' -> value = (value << 6) | 0x25;
                    case 'm' -> value = (value << 6) | 0x26;
                    case 'n' -> value = (value << 6) | 0x27;
                    case 'o' -> value = (value << 6) | 0x28;
                    case 'p' -> value = (value << 6) | 0x29;
                    case 'q' -> value = (value << 6) | 0x2A;
                    case 'r' -> value = (value << 6) | 0x2B;
                    case 's' -> value = (value << 6) | 0x2C;
                    case 't' -> value = (value << 6) | 0x2D;
                    case 'u' -> value = (value << 6) | 0x2E;
                    case 'v' -> value = (value << 6) | 0x2F;
                    case 'w' -> value = (value << 6) | 0x30;
                    case 'x' -> value = (value << 6) | 0x31;
                    case 'y' -> value = (value << 6) | 0x32;
                    case 'z' -> value = (value << 6) | 0x33;
                    case '0' -> value = (value << 6) | 0x34;
                    case '1' -> value = (value << 6) | 0x35;
                    case '2' -> value = (value << 6) | 0x36;
                    case '3' -> value = (value << 6) | 0x37;
                    case '4' -> value = (value << 6) | 0x38;
                    case '5' -> value = (value << 6) | 0x39;
                    case '6' -> value = (value << 6) | 0x3A;
                    case '7' -> value = (value << 6) | 0x3B;
                    case '8' -> value = (value << 6) | 0x3C;
                    case '9' -> value = (value << 6) | 0x3D;
                    case '+' -> value = (value << 6) | 0x3E;
                    case '/' -> value = (value << 6) | 0x3F;
                    case '=' -> {
                        append = false;
                        switch (j) {
                            case 2 -> buf.put((byte) ((value >>> 4) & 0xFF));
                            case 3 -> {
                                buf.put((byte) ((value >>> 10) & 0xFF));
                                buf.put((byte) ((value >>> 2) & 0xFF));
                            }
                        }
                    }
                    default ->
                        // Message message = ERR_BASE64_DECODE_INVALID_CHARACTER.get(encodedData,
                        // encodedData.charAt(i + j));
                            throw new ParseException("Invalid Base64 character " + encodedData.charAt(i + j), i + j);
                }
                if (!append) {
                    break;
                }
            }

            if (append) {
                buf.put((byte) ((value >>> 16) & 0xFF));
                buf.put((byte) ((value >>> 8) & 0xFF));
                buf.put((byte) (value & 0xFF));
            } else {
                break;
            }
        }

        buf.flip();
        byte[] returnArray = new byte[buf.limit()];
        buf.get(returnArray);
        return returnArray;
    }

}
