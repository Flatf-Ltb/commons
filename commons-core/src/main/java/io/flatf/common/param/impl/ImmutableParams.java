package io.flatf.common.param.impl;

import io.flatf.common.collections.MutableMaps;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.param.ParamKey;
import io.flatf.common.param.Params;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import static io.flatf.common.collections.ImmutableSets.newImmutableSet;
import static io.flatf.common.datetime.pattern.StandardPattern.fmt;
import static io.flatf.common.datetime.pattern.StandardPattern.toDate;
import static io.flatf.common.datetime.pattern.StandardPattern.toDateTime;
import static io.flatf.common.datetime.pattern.StandardPattern.toTime;
import static io.flatf.common.datetime.pattern.StandardPattern.toZonedDateTime;
import static io.flatf.common.lang.Validator.nonEmpty;
import static io.flatf.common.lang.Validator.nonNull;
import static io.flatf.common.lang.Validator.requiredLength;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class ImmutableParams implements Params {

    private static final Logger log = Log4j2LoggerFactory.getLogger(ImmutableParams.class);

    private final ImmutableMap<ParamKey, String> params;

    private final ImmutableSet<ParamKey> keys;

    /**
     * 根据传入的Key获取Map中的相应字段
     *
     * @param inputMap Map<String, ?>
     * @param keys     K[]
     * @throws NullPointerException     e
     * @throws IllegalArgumentException e
     */
    public ImmutableParams(@Nonnull Map<String, ?> inputMap, @Nonnull ParamKey... keys)
            throws NullPointerException, IllegalArgumentException {
        nonEmpty(inputMap, "inputMap");
        requiredLength(keys, 1, "keys");
        var map = MutableMaps.<ParamKey, String>newUnifiedMap();
        // 只加载指定Key对应的Value
        for (ParamKey key : keys) {
            if (inputMap.containsKey(key.getParamName())) {
                var input = inputMap.get(key.getParamName());
                if (input == null) {
                    throw new NullPointerException("Key -> [" + key.getParamName() + "] mapping value is null");
                }
                if (input instanceof String value) {
                    map.put(key, value);
                } else {
                    map.put(key, valueHandle(key, input));
                }
            } else {
                log.warn("Key -> [{}] mapping value not provided", key.getParamName());
            }
        }
        this.params = map.toImmutable();
        this.keys = newImmutableSet(keys);
    }

    private String valueHandle(ParamKey key, Object input) {
        String value = switch (key.getValueType()) {
            case DATE -> input instanceof LocalDate date ? fmt(date) : null;
            case TIME -> input instanceof LocalTime time ? fmt(time) : null;
            case DATETIME -> input instanceof LocalDateTime datetime ? fmt(datetime) : null;
            case ZONED_DATETIME -> input instanceof ZonedDateTime zDatetime ? fmt(zDatetime) : null;
            case INT -> Integer.toString(parseInt(input.toString()));
            case LONG -> Long.toString(parseLong(input.toString()));
            case DOUBLE -> Double.toString(parseDouble(input.toString()));
            case BOOLEAN -> Boolean.toString(parseBoolean(input.toString()));
            default -> Objects.toString(input, null);
        };
        if (value == null)
            throw new IllegalArgumentException("Key -> [" + key.getParamName() + "] mapping value is illegal, "
                    + "Type is [" + key.getValueType() + "], "
                    + "input value is [" + input + "]");
        return value;
    }


    /**
     * 根据传入的Key获取Properties中的相应字段
     *
     * @param prop Properties
     * @param keys K[]
     * @throws NullPointerException     e
     * @throws IllegalArgumentException e
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public ImmutableParams(@Nonnull Properties prop, @Nonnull ParamKey... keys)
            throws NullPointerException, IllegalArgumentException {
        this((Map) nonNull(prop, "prop"), nonNull(keys, "keys"));
    }

    /**
     * @param key K
     * @return boolean
     * @throws ParamGettingException e
     * @throws NullPointerException  e
     */
    @Override
    public boolean getBoolean(ParamKey key) throws ParamGettingException, NullPointerException {
        if (key.getValueType() != ValueType.BOOLEAN)
            throw new ParamGettingException(
                    "Key -> [" + key + "], ValueType is not BOOLEAN, valueType==" + key.getValueType());
        return parseBoolean(nonNull(params.get(key), key.getParamName()));
    }

    /**
     * @param key K
     * @return int
     * @throws ParamGettingException e
     * @throws NullPointerException  e
     * @throws NumberFormatException e
     */
    @Override
    public int getInt(ParamKey key) throws ParamGettingException, NullPointerException, NumberFormatException {
        if (key.getValueType() != ValueType.INT)
            throw new ParamGettingException(
                    "Key -> [" + key + "], ValueType is not [INT]. valueType==" + key.getValueType());
        return parseInt(nonNull(params.get(key), key.getParamName()));
    }

    /**
     * @param key K
     * @return long
     * @throws ParamGettingException e
     * @throws NullPointerException  e
     * @throws NumberFormatException e
     */
    @Override
    public long getLong(ParamKey key) throws ParamGettingException, NullPointerException, NumberFormatException {
        if (key.getValueType() != ValueType.LONG)
            throw new ParamGettingException(
                    "Key -> [" + key + "], ValueType is not [LONG]. valueType==" + key.getValueType());
        return parseLong(nonNull(params.get(key), key.getParamName()));
    }

    /**
     * @param key K
     * @return double
     * @throws ParamGettingException e
     * @throws NullPointerException  e
     * @throws NumberFormatException e
     */
    @Override
    public double getDouble(ParamKey key) throws ParamGettingException, NullPointerException, NumberFormatException {
        if (key.getValueType() != ValueType.DOUBLE)
            throw new ParamGettingException(
                    "Key -> [" + key + "], ValueType is not [DOUBLE], valueType==" + key.getValueType());
        return parseDouble(nonNull(params.get(key), key.getParamName()));
    }

    /**
     * @param key K
     * @return String
     * @throws ParamGettingException e
     * @throws NullPointerException  e
     */
    @Override
    public String getString(ParamKey key) throws ParamGettingException, NullPointerException {
        if (key.getValueType() != ValueType.STRING)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ValueType is not [STRING], paramType==" + key.getValueType());
        return nonNull(params.get(key), key.getParamName());
    }

    @Override
    public LocalDate getDate(ParamKey key) {
        if (key.getValueType() != ValueType.DATE)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ValueType is not [DATE], paramType==" + key.getValueType());
        return toDate(nonNull(params.get(key), key.getParamName()));
    }

    @Override
    public LocalTime getTime(ParamKey key) {
        if (key.getValueType() != ValueType.TIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ValueType is not [TIME], paramType==" + key.getValueType());
        return toTime(nonNull(params.get(key), key.getParamName()));
    }

    @Override
    public LocalDateTime getDateTime(ParamKey key) {
        if (key.getValueType() != ValueType.DATETIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ValueType is not [DATETIME], paramType==" + key.getValueType());
        return toDateTime(nonNull(params.get(key), key.getParamName()));
    }

    @Override
    public ZonedDateTime getZonedDateTime(ParamKey key) {
        if (key.getValueType() != ValueType.ZONED_DATETIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ValueType is not [ZONED_DATETIME], paramType==" + key.getValueType());
        return toZonedDateTime(nonNull(params.get(key), key.getParamName()));
    }

    @Override
    public boolean isImmutable() {
        return true;
    }

    @Override
    public Set<ParamKey> getParamKeys() {
        return Set.of(keys.toArray(new ParamKey[keys.size()]));
    }

    @Override
    public void showParams(Logger log) {
        if (log == null)
            params.forEachKeyValue((key, value) -> System.out.println(
                    "Param ID==" + key.getParamId() + ", ParamName -> " + key.getParamName() + ", Value -> " + value));
        else
            params.forEachKeyValue((key, value) -> log.info("Param ID=={}, ParamName=={}, Value -> {}",
                    key.getParamId(), key.getParamName(), value));
    }

}
