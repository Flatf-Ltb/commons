package io.flatf.common.param.impl;

import io.flatf.common.collections.MutableMaps;
import io.flatf.common.collections.MutableSets;
import io.flatf.common.datetime.pattern.StandardPattern;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import io.flatf.common.param.ParamKey;
import io.flatf.common.param.Params;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.api.set.MutableSet;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static io.flatf.common.collections.MapUtil.nonEmpty;

public class MutableParams implements Params {

    private static final Logger log = Log4j2LoggerFactory.getLogger(MutableParams.class);

    private final MutableIntObjectMap<String> params = MutableMaps.newIntObjectMap();

    private final MutableSet<ParamKey> keys = MutableSets.newUnifiedSet();

    public MutableParams() {
        this(() -> null);
    }

    /**
     * @param initializer Supplier<Map<ParamKey, String>>
     */
    public MutableParams(@Nonnull Supplier<Map<ParamKey, String>> initializer) {
        this(initializer.get());
    }

    /**
     * @param map Map<ParamKey, String>
     */
    public MutableParams(Map<ParamKey, String> map) {
        if (nonEmpty(map))
            map.forEach((key, value) -> params.put(key.getParamId(), value));
        else
            log.warn("Input map is empty when initialized");
    }

    /**
     * @param key K
     * @return boolean
     */
    @Override
    public boolean getBoolean(ParamKey key) {
        if (key.getValueType() != ValueType.BOOLEAN)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [BOOLEAN], ParamType==" + key.getValueType());
        return Boolean.parseBoolean(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return int
     */
    @Override
    public int getInt(ParamKey key) {
        if (key.getValueType() != ValueType.INT)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [INT], ParamType==" + key.getValueType());
        return Integer.parseInt(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return long
     */
    @Override
    public long getLong(ParamKey key) {
        if (key.getValueType() != ValueType.LONG)
            throw new ParamGettingException(
                    "Key -> " + key + " ParamType is not [LONG], ParamType==" + key.getValueType());
        return Long.parseLong(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return double
     */
    @Override
    public double getDouble(ParamKey key) {
        if (key.getValueType() != ValueType.DOUBLE)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [DOUBLE], ParamType==" + key.getValueType());
        return Double.parseDouble(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return String
     */
    @Override
    public String getString(ParamKey key) {
        if (key.getValueType() != ValueType.STRING)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [STRING], ParamType==" + key.getValueType());
        return params.get(key.getParamId());
    }

    /**
     * @param key K
     * @return LocalDate
     */
    @Override
    public LocalDate getDate(ParamKey key) {
        if (key.getValueType() != ValueType.DATE)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [DATE], ParamType==" + key.getValueType());
        return StandardPattern.toDate(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return LocalTime
     */
    @Override
    public LocalTime getTime(ParamKey key) {
        if (key.getValueType() != ValueType.TIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [TIME], ParamType==" + key.getValueType());
        return StandardPattern.toTime(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getDateTime(ParamKey key) {
        if (key.getValueType() != ValueType.DATETIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [DATETIME], ParamType==" + key.getValueType());
        return StandardPattern.toDateTime(params.get(key.getParamId()));
    }

    /**
     * @param key K
     * @return ZonedDateTime
     */
    @Override
    public ZonedDateTime getZonedDateTime(ParamKey key) {
        if (key.getValueType() != ValueType.ZONED_DATETIME)
            throw new ParamGettingException(
                    "Key -> [" + key + "] ParamType is not [ZONED_DATETIME], ParamType==" + key.getValueType());
        return StandardPattern.toZonedDateTime(params.get(key.getParamId()));
    }

    @Override
    public boolean isImmutable() {
        return false;
    }

    /**
     * @param key   K
     * @param value boolean
     */
    public void putParam(ParamKey key, boolean value) {
        keys.add(key);
        params.put(key.getParamId(), Boolean.toString(value));
    }

    /**
     * @param key   K
     * @param value int
     */
    public void putParam(ParamKey key, int value) {
        keys.add(key);
        params.put(key.getParamId(), Integer.toString(value));
    }

    /**
     * @param key   K
     * @param value long
     */
    public void putParam(ParamKey key, long value) {
        keys.add(key);
        params.put(key.getParamId(), Long.toString(value));
    }

    /**
     * @param key   K
     * @param value double
     */
    public void putParam(ParamKey key, double value) {
        keys.add(key);
        params.put(key.getParamId(), Double.toString(value));
    }

    /**
     * @param key   K
     * @param value String
     */
    public void putParam(ParamKey key, String value) {
        keys.add(key);
        params.put(key.getParamId(), value);
    }

    /**
     * @param key   K
     * @param value LocalDate
     */
    public void putParam(ParamKey key, LocalDate value) {
        keys.add(key);
        params.put(key.getParamId(), StandardPattern.fmt(value));
    }

    /**
     * @param key   K
     * @param value LocalTime
     */
    public void putParam(ParamKey key, LocalTime value) {
        keys.add(key);
        params.put(key.getParamId(), StandardPattern.fmt(value));
    }

    /**
     * @param key   K
     * @param value LocalDateTime
     */
    public void putParam(ParamKey key, LocalDateTime value) {
        keys.add(key);
        params.put(key.getParamId(), StandardPattern.fmt(value));
    }

    /**
     * @param key   K
     * @param value ZonedDateTime
     */
    public void putParam(ParamKey key, ZonedDateTime value) {
        keys.add(key);
        String fmt = StandardPattern.fmt(value);
        params.put(key.getParamId(), fmt);
    }

    @Override
    public Set<ParamKey> getParamKeys() {
        return Set.of(keys.toArray(new ParamKey[keys.size()]));
    }

}
