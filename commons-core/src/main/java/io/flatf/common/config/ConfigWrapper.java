package io.flatf.common.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Properties;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import static io.flatf.common.functional.Functions.getOrDefault;
import static io.flatf.common.functional.Functions.getOrThrows;
import static io.flatf.common.lang.Validator.nonNull;
import static io.flatf.common.util.StringSupport.nonEmpty;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public final class ConfigWrapper<O extends ConfigOption> {

    private final String module;

    private final Properties properties;

    public ConfigWrapper(@Nonnull Properties properties) {
        this("", properties);
    }

    public ConfigWrapper(@Nullable String module, @Nonnull Properties properties) {
        nonNull(properties, "config");
        this.properties = properties;
        this.module = nonEmpty(module)
                ? module.endsWith(".")
                  ? module
                  : module + "."
                : "";
    }

    /**
     * 是否存在此项配置, 并且配置不为空
     *
     * @param option OP
     * @return boolean
     */
    public boolean hasOption(@Nonnull O option) {
        return properties.containsKey(option.getConfigName(module));
    }

    /**
     * 是否存在此项配置, 或此项配置为空
     *
     * @param option OP
     * @return boolean
     */
    public boolean hasOptionOrNull(@Nonnull O option) {
        return properties.containsKey(option.getConfigName(module));
    }

    /**
     * 获取[boolean]配置值, 如果未配置, 默认值[false]
     *
     * @param option OP
     * @return boolean
     */
    public boolean getBoolean(@Nonnull O option) {
        return getBoolean(option, false);
    }

    /**
     * 获取[boolean]配置值, 如果未配置, 使用指定默认值
     *
     * @param option     OP
     * @param defaultVal boolean
     * @return boolean
     */
    public boolean getBoolean(@Nonnull O option, boolean defaultVal) {
        return getOrDefault(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseBoolean(properties.getProperty(option.getConfigName(module))),
                defaultVal);
    }

    /**
     * @param option OP
     * @return boolean
     * @throws NullPointerException e
     */
    public boolean getBooleanOrThrows(@Nonnull O option) throws NullPointerException {
        return getOrThrows(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseBoolean(properties.getProperty(option.getConfigName(module))),
                new NullPointerException(option.getConfigName(module)));
    }


    /**
     * 获取[int]配置值, 如果未配置, 默认值[0]
     *
     * @param option OP
     * @return int
     */
    public int getInt(@Nonnull O option) {
        return getInt(option, 0);
    }

    /**
     * 获取[int]配置值, 如果未配置, 使用指定默认值
     *
     * @param option     OP
     * @param defaultVal int
     * @return int
     */
    public int getInt(@Nonnull O option, int defaultVal) {
        return getOrDefault(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseInt(properties.getProperty(option.getConfigName(module))),
                defaultVal);
    }

    /**
     * @param option OP
     * @return int
     * @throws NullPointerException e
     */
    public int getIntOrThrows(@Nonnull O option) throws NullPointerException {
        return getOrThrows(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseInt(properties.getProperty(option.getConfigName(module))),
                new NullPointerException(option.getConfigName(module)));
    }

    /**
     * @param option OP
     * @param predicate IntPredicate
     * @return int
     * @throws NullPointerException e
     * @throws IllegalArgumentException e
     */
    public int getIntOrThrows(@Nonnull O option, IntPredicate predicate)
            throws NullPointerException, IllegalArgumentException {
        int value = getIntOrThrows(option);
        if (predicate.test(value))
            return value;
        else
            throw new IllegalArgumentException("Illegal argument -> " + option.getConfigName(module));
    }


    /**
     * 获取[long]配置值, 如果未配置, 默认值[0L]
     *
     * @param option OP
     * @return long
     */
    public long getLong(@Nonnull O option) {
        return getLong(option, 0L);
    }

    /**
     * 获取[long]配置值, 如果未配置, 使用指定默认值
     *
     * @param option     OP
     * @param defaultVal long
     * @return long
     */
    public long getLong(@Nonnull O option, long defaultVal) {
        return getOrDefault(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseLong(properties.getProperty(option.getConfigName(module))),
                defaultVal);
    }

    /**
     * @param option OP
     * @return long
     * @throws NullPointerException e
     */
    public long getLongOrThrows(@Nonnull O option) throws NullPointerException {
        return getOrThrows(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseLong(properties.getProperty(option.getConfigName(module))),
                new NullPointerException(option.getConfigName(module)));
    }

    /**
     * @param option OP
     * @param verify LongPredicate
     * @return long
     * @throws NullPointerException  exception
     * @throws IllegalArgumentException exception
     */
    public long getLongOrThrows(@Nonnull O option, LongPredicate verify)
            throws NullPointerException, IllegalArgumentException {
        long value = getLongOrThrows(option);
        if (verify.test(value))
            return value;
        else
            throw new IllegalArgumentException("Illegal argument -> " + option.getConfigName(module));
    }

    /**
     * 获取[double]配置值, 如果未配置, 默认值[0.0D]
     *
     * @param option OP
     * @return double
     */
    public double getDouble(@Nonnull O option) {
        return getDouble(option, 0.0D);
    }

    /**
     * 获取[double]配置值, 如果未配置, 使用指定默认值
     *
     * @param option     OP
     * @param defaultVal double
     * @return double
     */
    public double getDouble(@Nonnull O option, double defaultVal) {
        return getOrDefault(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseDouble(properties.getProperty(option.getConfigName(module))),
                defaultVal);
    }

    /**
     * @param option OP
     * @return double
     * @throws NullPointerException e
     */
    public double getDoubleOrThrows(@Nonnull O option) throws NullPointerException {
        return getOrThrows(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> parseDouble(properties.getProperty(option.getConfigName(module))),
                new NullPointerException(option.getConfigName(module)));
    }

    /**
     * @param option OP
     * @param verify DoublePredicate
     * @return double
     * @throws NullPointerException  e
     * @throws IllegalArgumentException e
     */
    public double getDoubleOrThrows(@Nonnull O option, DoublePredicate verify)
            throws NullPointerException, IllegalArgumentException {
        double value = getDoubleOrThrows(option);
        if (verify.test(value))
            return value;
        else
            throw new IllegalArgumentException("Illegal argument -> " + option.getConfigName(module));
    }

    /**
     * 获取[String]配置值, 如果未配置, 默认值[""]
     *
     * @param option OP
     * @return String
     */
    public String getString(@Nonnull O option) {
        return getString(option, "");
    }

    /**
     * 获取[String]配置值, 如果未配置, 使用指定默认值
     *
     * @param option     OP
     * @param defaultVal String
     * @return String
     */
    public String getString(@Nonnull O option, @Nonnull String defaultVal) {
        return getOrDefault(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> properties.getProperty(option.getConfigName(module)),
                defaultVal);
    }

    /**
     * @param option OP
     * @return String
     * @throws NullPointerException e
     */
    public String getStringOrThrows(@Nonnull O option) throws NullPointerException {
        return getOrThrows(
                () -> properties.containsKey(option.getConfigName(module)),
                () -> properties.getProperty(option.getConfigName(module)),
                new NullPointerException(option.getConfigName(module)));
    }

    /**
     * @param option OP
     * @param verify Predicate<String>
     * @return String
     * @throws NullPointerException  e
     * @throws IllegalArgumentException e
     */
    public String getStringOrThrows(@Nonnull O option, Predicate<String> verify)
            throws NullPointerException, IllegalArgumentException {
        String value = getStringOrThrows(option);
        if (verify.test(value))
            return value;
        else
            throw new IllegalArgumentException("Illegal argument -> " + option.getConfigName(module));
    }

}
