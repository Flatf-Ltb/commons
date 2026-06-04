package io.flatf.common.util;

/**
 * Utility class for working with byte arrays such as converting between byte
 * arrays and numbers.
 */
public final class BytesUtil {

    private BytesUtil() {
    }

    /**
     * @param bytes byte[]
     * @throws IllegalArgumentException iae
     */
    public static void checkBytesNotNull(byte[] bytes) throws IllegalArgumentException {
        if (bytes == null)
            throw new IllegalArgumentException("Byte array was null");
    }

    /**
     * Helper method for validating if an offset and length are valid for a given
     * byte array. Checks if the offset or length is negative or if the
     * offset+length would cause a read past the end of the byte array.
     *
     * @param bytesLength The length of the byte array to validate against
     * @param offset      The offset within the byte array
     * @param length      The length to read starting from the offset
     * @throws java.lang.IllegalArgumentException If any of the above conditions are
     *                                            violated.
     */
    public static void checkOffsetLength(int bytesLength, int offset, int length) throws IllegalArgumentException {
        // offset cannot be negative
        if (offset < 0)
            throw new IllegalArgumentException("The byte[] offset parameter cannot be negative");

        // length cannot be negative either
        if (length < 0)
            throw new IllegalArgumentException("The byte[] length parameter cannot be negative");

        // is it a valid offset? Must be < bytes.length if non-zero
        // if its zero, then the check below will validate if it would cause
        // a read past the length of the byte array
        if (offset != 0 && offset >= bytesLength)
            throw new IllegalArgumentException("The byte[] offset (" + offset + ") must be < the length of the byte[] length (" + bytesLength + ")");

        if (offset + length > bytesLength)
            throw new IllegalArgumentException("The offset+length (" + offset + length + ") would read past the end of the byte[] (length=" + bytesLength + ")");

    }

    /**
     * @param bytes          byte[]
     * @param offset         int
     * @param length         int
     * @param expectedLength int
     */
    public static void checkBytes(byte[] bytes, int offset, int length, int expectedLength)
            throws IllegalArgumentException {
        checkBytesNotNull(bytes);
        checkOffsetLength(bytes.length, offset, length);
        if (length != expectedLength)
            throw new IllegalArgumentException(
                    "Unexpected length of byte array [expected=" + expectedLength + ", actual=" + length + "]");
    }

    /**
     * @param value byte
     * @return byte[]
     */
    public static byte[] toByteArray(byte value) {
        return new byte[]{value};
    }

    /**
     * @param value short
     * @return byte[]
     */
    public static byte[] toBytes(short value) {
        byte[] buf = new byte[2];
        buf[0] = (byte) ((value >>> 8) & 0xFF);
        buf[1] = (byte) (value & 0xFF);
        return buf;
    }

    /**
     * @param value int
     * @return byte[]
     */
    public static byte[] toBytes(int value) {
        byte[] buf = new byte[4];
        buf[0] = (byte) ((value >>> 24) & 0xFF);
        buf[1] = (byte) ((value >>> 16) & 0xFF);
        buf[2] = (byte) ((value >>> 8) & 0xFF);
        buf[3] = (byte) (value & 0xFF);
        return buf;
    }

    /**
     * @param value long
     * @return byte[]
     */
    public static byte[] toBytes(long value) {
        byte[] buf = new byte[8];
        buf[0] = (byte) ((value >>> 56) & 0xFF);
        buf[1] = (byte) ((value >>> 48) & 0xFF);
        buf[2] = (byte) ((value >>> 40) & 0xFF);
        buf[3] = (byte) ((value >>> 32) & 0xFF);
        buf[4] = (byte) ((value >>> 24) & 0xFF);
        buf[5] = (byte) ((value >>> 16) & 0xFF);
        buf[6] = (byte) ((value >>> 8) & 0xFF);
        buf[7] = (byte) (value & 0xFF);
        return buf;
    }

    /**
     * @param bytes byte[]
     * @return byte
     */
    public static byte toByte(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toByte(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return byte
     */
    public static byte toByte(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 1);
        return bytes[offset];
    }

    /**
     * @param bytes byte[]
     * @return short
     */
    public static short toUnsignedByte(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toUnsignedByte(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return short
     */
    public static short toUnsignedByte(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 1);
        short v = 0;
        v |= (short) (bytes[offset] & 0xFF);
        return v;
    }

    /**
     * @param bytes byte[]
     * @return short
     */
    public static short toShort(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toShort(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return short
     */
    public static short toShort(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 2);
        short v = 0;
        v |= (short) (bytes[offset] & 0xFF);
        v <<= 8;
        v |= (short) (bytes[offset + 1] & 0xFF);
        return v;
    }

    /**
     * @param bytes byte[]
     * @return int
     */
    public static int toUnsignedShort(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toUnsignedShort(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return int
     */
    public static int toUnsignedShort(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 2);
        int v = 0;
        v |= bytes[offset] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 1] & 0xFF;
        return v;
    }

    /**
     * @param bytes byte[]
     * @return int
     */
    public static int toInt(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toInt(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return int
     */
    public static int toInt(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 4);
        int v = 0;
        v |= bytes[offset] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 1] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 2] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 3] & 0xFF;
        return v;
    }

    /**
     * @param bytes byte[]
     * @return long
     */
    public static long toUnsignedInt(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toUnsignedInt(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return long
     */
    public static long toUnsignedInt(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 4);
        long v = 0;
        v |= bytes[offset] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 1] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 2] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 3] & 0xFF;
        return v;
    }

    /**
     * @param bytes byte[]
     * @return long
     */
    public static long toLong(byte[] bytes) {
        checkBytesNotNull(bytes);
        return toLong(bytes, 0, bytes.length);
    }

    /**
     * @param bytes  byte[]
     * @param offset int
     * @param length int
     * @return long
     */
    public static long toLong(byte[] bytes, int offset, int length) {
        checkBytes(bytes, offset, length, 8);
        long v = 0;
        v |= bytes[offset] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 1] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 2] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 3] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 4] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 5] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 6] & 0xFF;
        v <<= 8;
        v |= bytes[offset + 7] & 0xFF;
        return v;
    }

}
