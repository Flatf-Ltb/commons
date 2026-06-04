package io.flatf.common.util;

import io.flatf.common.character.Charsets;
import io.flatf.common.lang.Validator;
import io.flatf.common.sys.LibraryPathManager;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Properties;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public final class PropertiesUtil {

    /**
     * @param url URL
     * @return Properties
     * @throws IOException ioe
     */
    public static Properties load(final URL url) throws IOException {
        Validator.nonNull(url, "url");
        return load(url.openStream());
    }

    /**
     * @param file File
     * @return Properties
     * @throws IOException ioe
     */
    public static Properties load(@Nonnull final File file) throws IOException {
        Validator.nonNull(file, "file");
        return load(new FileInputStream(file));
    }

    /**
     * @param text String
     * @return Properties
     * @throws IOException ioe
     */
    public static Properties load(@Nonnull final String text) throws IOException {
        return load(text, Charsets.UTF8);
    }

    /**
     * @param text    String
     * @param charset Charset
     * @return Properties
     * @throws IOException ioe
     */
    public static Properties load(@Nonnull final String text, @Nonnull final Charset charset) throws IOException {
        Validator.nonNull(text, "text");
        Validator.nonNull(charset, "charset");
        if (StringSupport.nonEmpty(text))
            return load(new ByteArrayInputStream(text.getBytes(charset)));
        return new Properties();
    }

    /**
     * @param stream InputStream
     * @return Properties
     * @throws IOException ioe
     */
    public static Properties load(@Nonnull final InputStream stream) throws IOException {
        Validator.nonNull(stream, "stream");
        Properties props = new Properties();
        try (final InputStream in = stream) {
            props.load(in);
        }
        return props;
    }

    /**
     * @param props Properties
     * @param key   String
     * @return String
     */
    public static String getString(@Nonnull final Properties props, final String key) {
        return props.getProperty(key, "");
    }

    /**
     * @param props Properties
     * @param key   String
     * @return boolean
     * @throws NumberFormatException e
     */
    public static boolean getBoolean(@Nonnull final Properties props, final String key)
            throws NumberFormatException {
        String prop = props.getProperty(key);
        if (StringSupport.isNullOrEmpty(prop))
            return false;
        return parseBoolean(prop);
    }

    /**
     * @param props Properties
     * @param key   String
     * @return int
     * @throws NumberFormatException e
     */
    public static int getInt(@Nonnull final Properties props, final String key) throws NumberFormatException {
        String prop = props.getProperty(key);
        return StringSupport.isNullOrEmpty(prop) ? 0 : parseInt(prop);
    }

    /**
     * @param props Properties
     * @param key   String
     * @return double
     * @throws NumberFormatException e
     */
    public static double getDouble(@Nonnull final Properties props, final String key)
            throws NumberFormatException {
        String prop = props.getProperty(key);
        return StringSupport.isNullOrEmpty(prop) ? 0.0D : parseDouble(prop);
    }

    /**
     * @param props Properties
     */
    public static void show(@Nonnull final Properties props) {
        show(props, null);
    }

    /**
     * @param props Properties
     * @param log   Logger
     */
    public static void show(@Nonnull final Properties props, @Nullable final Logger log) {
        ArrayList<Entry<Object, Object>> list = new ArrayList<>(props.entrySet());
        for (int i = 0; i < list.size(); i++) {
            Entry<Object, Object> entry = list.get(i);
            if (log == null)
                System.out.println("Property " + i + " : key -> " + entry.getKey() + ", value -> " + entry.getValue());
            else
                log.info("Property {} : key -> {}, value -> {}", i, entry.getKey(), entry.getValue());
        }

    }

    static void main(String[] args) {
        System.out.println("---old---");
        PropertiesUtil.show(System.getProperties());
        try {
            LibraryPathManager.addLibraryDir("~/java_lib");
        } catch (Exception ignored) {
        }
        System.out.println("---new---");
        PropertiesUtil.show(System.getProperties());
    }

}
