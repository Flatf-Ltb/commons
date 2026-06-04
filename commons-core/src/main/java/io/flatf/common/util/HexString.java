package io.flatf.common.util;

import java.util.Arrays;

/**
 * Utility class for representing hex strings. Instances are immutable. Hex
 * digits are always in caps.
 * <p>
 * Primarily used to aid testing, the implementation is naive and not
 * performant. <b>NOT RECOMMENDED FOR USE IN PRODUCTION</b>.
 */
public final class HexString {

    public static final HexString EMPTY = new HexString(new byte[0], "");

    private final String hex;
    private final byte[] bytes;

    private HexString(byte[] bytes, String hex) {
        this.bytes = bytes;
        this.hex = hex;
    }

    /**
     * Create a new <code>HexString</code> instance from the given hexadecimal
     * string. If <code>null</code> or an empty string is passed, will return a
     * <code>HexString</code> instance with empty hex value and zero-length byte
     * array.
     *
     * @param hex The hexadecimal string
     * @return Immutable <code>HexString</code> instance encapsulating the
     * hexadecimal string
     * @throws IllegalArgumentException if malformed hex string (invalid or odd
     *                                  number of characters) is passed
     */
    public static HexString valueOf(String hex) {
        return StringSupport.isNullOrEmpty(hex) ? EMPTY : createHexString(hex);
    }

    /**
     * @param hex String
     * @return HexString
     * @throws IllegalArgumentException iae
     */
    private static HexString createHexString(String hex) throws IllegalArgumentException {
        // though method is private, leaving checks in place to show intent
        if (hex == null)
            throw new IllegalArgumentException("hex string argument cannot be null; use HexString.EMPTY instead");
        if (hex.isEmpty())
            throw new IllegalArgumentException("hex string argument cannot be empty; use HexString.EMPTY instead");
        byte[] bytes = HexUtil.toByteArray(hex);
        return new HexString(bytes, hex.toUpperCase());
    }

    /**
     * Create a new <code>HexString</code> instance from the given byte array. If
     * <code>null</code> or zero-length byte array is passed, will return a
     * <code>HexString</code> instance with empty hex value and zero-length byte
     * array.
     *
     * @param bytes The byte array
     * @return Immutable <code>HexString</code> instance encapsulating the byte
     * array
     */
    public static HexString valueOf(byte[] bytes) {
        return ArrayUtil.isNullOrEmpty(bytes) ? EMPTY : createHexString(bytes);
    }

    /**
     * @param bytes byte[]
     * @return HexString
     * @throws IllegalArgumentException iae
     */
    private static HexString createHexString(byte[] bytes) throws IllegalArgumentException {
        // though the method is private, leaving checks in place to show intent
        if (bytes == null)
            throw new IllegalArgumentException("bytes argument cannot be null; use HexString.NULL instead");
        if (bytes.length == 0)
            throw new IllegalArgumentException("bytes argument cannot be zero length; use HexString.NULL instead");
        byte[] copyOfBytes = copyByteArray(bytes);
        String hex = HexUtil.toHex(copyOfBytes).toUpperCase();
        return new HexString(copyOfBytes, hex);
    }

    /**
     * @param bytes byte[]
     * @return byte[]
     */
    private static byte[] copyByteArray(byte[] bytes) {
        return Arrays.copyOf(bytes, bytes.length);
    }

    /**
     * @return The instance as an unformatted hex string - i.e. \"AB45C03F\"
     */
    public String asString() {
        return hex;
    }

    /**
     * N.B. <code>asBytes()</code> returns a copy of the underlying byte array
     *
     * @return The instance as its underlying byte array
     */
    public byte[] asBytes() {
        return copyByteArray(bytes);
    }

    @Override
    public String toString() {
        return "0x" + hex;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof HexString hexString) {
            return this.hex.equals(hexString.hex);
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return this.hex.hashCode();
    }

}
