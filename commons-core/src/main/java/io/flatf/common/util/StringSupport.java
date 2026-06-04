package io.flatf.common.util;

import io.flatf.common.character.Charsets;
import io.flatf.common.character.Separator;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public final class StringSupport {

    public static final String CONST_NULL = "null";

    public static final String CONST_EMPTY = "";

    private StringSupport() {
    }

    /**
     * @param i int
     * @return String
     */
    @Nonnull
    public static String toString(int i) {
        return Integer.toString(i);
    }

    /**
     * @param is int[]
     * @return String
     */
    @Nonnull
    public static String toString(@Nullable int[] is) {
        return is == null ? CONST_NULL : Arrays.toString(is);
    }

    /**
     * @param l long
     * @return String
     */
    @Nonnull
    public static String toString(long l) {
        return Long.toString(l);
    }

    /**
     * @param ls long[]
     * @return String
     */
    @Nonnull
    public static String toString(@Nullable long[] ls) {
        return ls == null ? CONST_NULL : Arrays.toString(ls);
    }

    /**
     * @param d double
     * @return String
     */
    @Nonnull
    public static String toString(double d) {
        return Double.toString(d);
    }

    /**
     * @param ds double[]
     * @return String
     */
    @Nonnull
    public static String toString(@Nullable double[] ds) {
        return ds == null ? CONST_NULL : Arrays.toString(ds);
    }

    /**
     * @param bs byte[]
     * @return String
     */
    @Nonnull
    public static String toString(byte[] bs) {
        return toString(Charsets.UTF8, bs);
    }

    /**
     * @param charset Charset
     * @param bytes      byte[]
     * @return String
     */
    @Nonnull
    public static String toString(Charset charset, byte[] bytes) {
        if (bytes == null)
            return CONST_NULL;
        return new String(bytes, charset == null ? Charsets.UTF8 : charset);
    }

    /**
     * @param cs char[]
     * @return String
     */
    @Nonnull
    public static String toString(char[] cs) {
        return cs == null ? CONST_NULL : new String(cs);
    }

    /**
     * @param strings String[]
     * @return String
     */
    public static String toString(@Nullable String[] strings) {
        return strings == null ? CONST_NULL : toString(String::toString, strings);
    }

    /**
     * call toString() method
     *
     * @param obj Object
     * @return String
     */
    @Nonnull
    public static <T> String toString(@Nullable T obj) {
        if (obj == null)
            return CONST_NULL;
        return obj.toString();
    }

    /**
     * @param objs T[]
     * @param <T>  T
     * @return String
     */
    @Nonnull
    @SafeVarargs
    public static <T> String toString(@Nullable T... objs) {
        return toString(StringSupport::toString, objs);
    }

    /**
     * @param toStringFunc Function<T, String>
     * @param objs         T[]
     * @param <T>          T
     * @return String
     */
    @Nonnull
    @SafeVarargs
    public static <T> String toString(Function<T, String> toStringFunc, T... objs) {
        if (objs == null)
            return CONST_EMPTY;
        var builder = new StringBuilder(objs.length * 8).append('[');
        for (int i = 0, j = objs.length - 1; i < objs.length; i++) {
            builder.append(toStringFunc.apply(objs[i]));
            if (i < j)
                builder.append(", ");
        }
        return builder.append(']').toString();
    }

    /**
     * Used : <br>
     * org.apache.commons.lang3.builder.ToStringBuilder <br>
     * .reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE, false)
     *
     * @param obj Object
     * @return String
     */
    @Nonnull
    public static String toStringShortPrefixStyle(Object obj) {
        return toStringWithStyle(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /**
     * Used : <br>
     * org.apache.commons.lang3.builder.ToStringBuilder <br>
     * .reflectionToString(obj, ToStringStyle.JSON_STYLE, false)
     *
     * @param obj Object
     * @return String
     */
    @Nonnull
    public static String toStringJsonStyle(Object obj) {
        return toStringWithStyle(obj, ToStringStyle.JSON_STYLE);
    }

    /**
     * Used : <br>
     * org.apache.commons.lang3.builder.ToStringBuilder<br>
     * .reflectionToString(obj, style, false)
     *
     * @param obj   Object
     * @param style ToStringStyle
     * @return String
     */
    @Nonnull
    public static String toStringWithStyle(Object obj, ToStringStyle style) {
        return obj == null ? CONST_NULL : reflectionToString(obj, style, false);
    }

    /**
     * @param value String
     * @return boolean
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * @param value String
     * @return boolean
     */
    public static boolean nonEmpty(String value) {
        return value != null && !value.isEmpty();
    }

    /**
     * @param value    String
     * @param defValue String
     * @return String
     */
    public static String requireNonEmptyElse(String value, String defValue) {
        return nonEmpty(value) ? value : defValue;
    }

    /**
     * @param value    String
     * @param supplier Supplier<String>
     * @return String
     */
    public static String requireNonEmptyElseGet(String value, Supplier<String> supplier) {
        return nonEmpty(value) ? value : supplier.get();
    }

    /**
     * @param value String
     * @param func  Function<T, String>
     * @return String
     */
    public static <T> String requireNonEmptyElseGet(String value, Function<T, String> func, T t) {
        return nonEmpty(value) ? value : func.apply(t);
    }

    /**
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean isEquals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    /**
     * @param str1 String
     * @param str2 String
     * @return boolean
     */
    public static boolean nonEquals(String str1, String str2) {
        return !isEquals(str1, str2);
    }

    /**
     * 检查输入参数是否为数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean isDecimal(String str) {
        // null或空字符串
        if (isNullOrEmpty(str))
            return false;
        // 长度为1, 则判断是否是数字字符
        if (str.length() == 1)
            return str.charAt(0) >= '0' && str.charAt(0) <= '9';
        else {
            // 定义开始检查索引
            int offset = 0;
            // 判断是否负数, 如果是负数, 跳过第一位的检查
            if (isAllowedNonDigit(str.charAt(0)))
                offset = 1;
            // 定义结束位置
            int end = str.length();
            // 判断最后一个字符是否为long double float的写法
            if (isAllowedNonDigit(str.charAt(str.length() - 1)))
                end = str.length() - 1;
            // 如果没有数字可以检查且(第一位)与(最后一位)都跳过了检查,
            // 则[offset == endPoint], 此时输入参数不是数字
            if (offset == end)
                return false;
            // 是否已出现小数点
            boolean hasDecimalPoint = false;
            for (; offset < end; offset++) {
                // 判断每个字母是否为数字
                char c = str.charAt(offset);
                if (!(c >= '0' && c <= '9')) {
                    // 已出现小数点后再出现其他任何字符, 返回false
                    if (hasDecimalPoint)
                        return false;
                        // 标识出现了一个小数点
                    else if (c == '.')
                        hasDecimalPoint = true;
                        // 出现其他字符返回false
                    else
                        return false;
                }
            }
            return true;
        }
    }

    private static boolean isAllowedNonDigit(char c) {
        return switch (c) {
            case '-', 'L', 'l', 'D', 'd', 'F', 'f' -> true;
            default -> false;
        };
    }


    /**
     * 检查输入参数是否非数字
     *
     * @param str String
     * @return boolean
     */
    public static boolean notDecimal(String str) {
        return !isDecimal(str);
    }

    /**
     * 删除除字符串中的非数字
     *
     * @param str String
     * @return String
     */
    @Nonnull
    public static String removeNonDigits(String str) {
        if (isNullOrEmpty(str))
            return CONST_EMPTY;
        StringBuilder builder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ('0' <= c && c <= '9')
                builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 删除除字符串中的非字母
     *
     * @param str String
     * @return String
     */
    @Nonnull
    public static String removeNonAlphabet(String str) {
        if (isNullOrEmpty(str))
            return CONST_EMPTY;
        StringBuilder builder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'))
                builder.append(c);
        }
        return builder.toString();
    }

    /**
     * 去除分割符<b> "." </b>,<b> "-" </b>,<b> "_" </b>,<b> "/" </b>,<b> "\" </b>
     *
     * @param str String
     * @return String
     */
    public static String removeSplitChar(String str) {
        return isNullOrEmpty(str) ? CONST_EMPTY
                : str.replace(".", "").replace("-", "").replace("_", "").replace("/", "").replace("\\", "");
    }

    /**
     * @param gbkStr String
     * @return String
     */
    public static String conversionGbkToUtf8(String gbkStr) {
        return conversionTo(gbkStr, Charsets.GBK, Charsets.UTF8);
    }

    /**
     * @param utf8Str String
     * @return String
     */
    public static String conversionUtf8ToGbk(String utf8Str) {
        return conversionTo(utf8Str, Charsets.UTF8, Charsets.GBK);
    }

    /**
     * @param gbkStr String
     * @return String
     */
    public static String conversionGb2312ToUtf8(String gbkStr) {
        return conversionTo(gbkStr, Charsets.GB2312, Charsets.UTF8);
    }

    /**
     * @param utf8Str String
     * @return String
     */
    public static String conversionUtf8ToGb2312(String utf8Str) {
        return conversionTo(utf8Str, Charsets.UTF8, Charsets.GB2312);
    }

    /**
     * @param str     String
     * @param charset Charset
     * @return String
     */
    public static String conversionToUtf8(String str, Charset charset) {
        return conversionTo(str, charset, Charsets.UTF8);
    }

    /**
     * @param str           String
     * @param sourceCharset Charset
     * @param targetCharset Charset
     * @return String
     */
    public static String conversionTo(String str, Charset sourceCharset, Charset targetCharset) {
        return str == null ? null : new String(str.getBytes(sourceCharset), targetCharset);
    }

    /**
     * 是否为路径
     *
     * @param path String
     * @return boolean
     */
    public static boolean isPath(String path) {
        return !isNullOrEmpty(path) && (path.endsWith("/") || path.endsWith("\\"));
    }

    /**
     * 是否为路径
     *
     * @param path String
     * @return boolean
     */
    public static boolean notPath(String path) {
        return !isPath(path);
    }

    /**
     * 修补路径
     *
     * @param path String
     * @return String
     */
    public static String fixPath(String path) {
        if (isNullOrEmpty(path)) return "/";
        else if (path.endsWith("/") || path.endsWith("\\")) return path;
        else return path + Separator.FILE_SEPARATOR;
    }

    /**
     * 使用','将字符串连接
     *
     * @param strings String[]
     * @return String
     */
    public static String concatenateStr(String... strings) {
        return concatenateStr(strings.length * 16 + strings.length, ',', strings);
    }

    /**
     * 指定缓冲区长度, 使用','将字符串连接
     *
     * @param capacity 缓冲区长度
     * @param strings  字符串数组
     * @return String
     */
    public static String concatenateStr(int capacity, String... strings) {
        return concatenateStr(capacity, ',', strings);
    }

    /**
     * 使用指定符号将字符串连接
     *
     * @param symbol  连接符号
     * @param strings 字符串数组
     * @return String
     */
    public static String concatenateStr(char symbol, String... strings) {
        return concatenateStr(strings.length * 16 + strings.length, symbol, strings);
    }

    /**
     * 指定缓冲区长度, 使用指定符号将字符串连接
     *
     * @param capacity 缓冲区长度
     * @param symbol   连接符号
     * @param strings  字符串数组
     * @return String
     */
    public static String concatenateStr(int capacity, char symbol, String... strings) {
        if (strings == null || strings.length == 0)
            return CONST_EMPTY;
        StringBuilder builder = new StringBuilder(capacity);
        for (int i = 0; i < strings.length; i++) {
            builder.append(strings[i]);
            if (i < strings.length - 1)
                builder.append(symbol);
        }
        return builder.toString();
    }

    /**
     * Get 7-bit ASCII character array from input String. The lower 7 bits of each
     * character in the input string is assumed to be the ASCII character value.
     *
     * <pre>
     * Hexadecimal - Character
     *
     * | 00 NUL| 01 SOH| 02 STX| 03 ETX| 04 EOT| 05 ENQ| 06 ACK| 07 BEL|
     * | 08 BS | 09 HT | 0A NL | 0B VT | 0C NP | 0D CR | 0E SO | 0F SI |
     * | 10 DLE| 11 DC1| 12 DC2| 13 DC3| 14 DC4| 15 NAK| 16 SYN| 17 ETB|
     * | 18 CAN| 19 EM | 1A SUB| 1B ESC| 1C FS | 1D GS | 1E RS | 1F US |
     * | 20 SP | 21  ! | 22  " | 23  # | 24  $ | 25  % | 26  & | 27  ' |
     * | 28  ( | 29  ) | 2A  * | 2B  + | 2C  , | 2D  - | 2E  . | 2F  / |
     * | 30  0 | 31  1 | 32  2 | 33  3 | 34  4 | 35  5 | 36  6 | 37  7 |
     * | 38  8 | 39  9 | 3A  : | 3B  ; | 3C  < | 3D  = | 3E  > | 3F  ? |
     * | 40  @ | 41  A | 42  B | 43  C | 44  D | 45  E | 46  F | 47  G |
     * | 48  H | 49  I | 4A  J | 4B  K | 4C  L | 4D  M | 4E  N | 4F  O |
     * | 50  P | 51  Q | 52  R | 53  S | 54  T | 55  U | 56  V | 57  W |
     * | 58  X | 59  Y | 5A  Z | 5B  [ | 5C  \ | 5D  ] | 5E  ^ | 5F  _ |
     * | 60  ` | 61  a | 62  b | 63  c | 64  d | 65  e | 66  f | 67  g |
     * | 68  h | 69  i | 6A  j | 6B  k | 6C  l | 6D  m | 6E  n | 6F  o |
     * | 70  p | 71  q | 72  r | 73  s | 74  t | 75  u | 76  v | 77  w |
     * | 78  x | 79  y | 7A  z | 7B  { | 7C  | | 7D  } | 7E  ~ | 7F DEL|
     * </pre>
     */
    public static byte[] getAsciiBytes(String input) {
        char[] c = input.toCharArray();
        byte[] b = new byte[c.length];
        for (int i = 0; i < c.length; i++)
            b[i] = (byte) (c[i] & 0x007F);
        return b;
    }

    /**
     * @param input byte[]
     * @return String
     */
    public static String getAsciiString(byte[] input) {
        StringBuilder builder = new StringBuilder(input.length);
        for (byte b : input)
            builder.append((char) b);
        return builder.toString();
    }

    public static void main(String[] args) {

        System.out.println(toString(11));
        System.out.println(fixPath(null));
        System.out.println(fixPath("ddd"));
        System.out.println(fixPath("/user/"));

        System.out.println(isDecimal("-l"));
        System.out.println(isDecimal("877F"));
        System.out.println(isDecimal("877l"));
        System.out.println(isDecimal("877S"));
        System.out.println(isDecimal("877d"));
        System.out.println(isDecimal("877D"));
        System.out.println(isDecimal("-877"));
        System.out.println(isDecimal("-877L"));
        System.out.println(isDecimal("8.77L"));
        System.out.println(isDecimal(".877"));
        System.out.println(isDecimal("-.877"));
        System.out.println(isDecimal("-.87.7"));

        System.out.println(concatenateStr("A", "BB", "CCC"));
        System.out.println(concatenateStr("A", "BB", null));
        System.out.println(concatenateStr("A", "BB", "", null, "null"));
        System.out.println(concatenateStr("A", "BB", "", null));
        System.out.println(concatenateStr('%', "A", "BB", "", null));
        System.out.println(concatenateStr(null, null));

        System.out.println(removeNonDigits("fn909aje125f13de3132fde31dew"));

    }

}
