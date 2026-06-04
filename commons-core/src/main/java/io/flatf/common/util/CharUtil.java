package io.flatf.common.util;

public final class CharUtil {

    private CharUtil() {
    }

    /**
     * @param c char
     * @return int
     */
    public static int decimalCharToInt(char c) {
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
            default -> throw new IllegalArgumentException(
                    "The character [" + c + "] does not represent a valid hex digit");
        };
    }


}
