package io.flatf.common.character;

import io.flatf.common.util.StringSupport;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface Charsets {

    /**
     * @param name String
     * @return Charset
     */
    static Charset forName(String name) {
        if (StringSupport.isNullOrEmpty(name))
            return SYS_DEFAULT;
        return Charset.forName(name);
    }

    Charset SYS_DEFAULT = Charset.defaultCharset();

    Charset ASCII = StandardCharsets.US_ASCII;

    Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;

    Charset UTF8 = StandardCharsets.UTF_8;

    Charset UTF16 = StandardCharsets.UTF_16;

    Charset UTF32 = Charset.forName("UTF-32");

    Charset BIG5 = Charset.forName("Big5");

    Charset GBK = Charset.forName("GBK");

    Charset GB2312 = Charset.forName("GB2312");

    static void main(String[] args) {
        Charset.availableCharsets().values().forEach(System.out::println);
    }

}
