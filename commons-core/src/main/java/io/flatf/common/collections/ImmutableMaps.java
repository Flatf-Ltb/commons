package io.flatf.common.collections;

import io.flatf.common.lang.Validator;
import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.map.immutable.ImmutableMapFactoryImpl;
import org.eclipse.collections.impl.map.immutable.primitive.ImmutableIntObjectMapFactoryImpl;
import org.eclipse.collections.impl.map.sorted.immutable.ImmutableSortedMapFactoryImpl;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImmutableMaps {

    private ImmutableMaps() {
    }

    /**
     * @param <V>     Value type
     * @param keyFunc IntFunction<V>
     * @param values  RichIterable<V>
     * @return ImmutableIntObjectMap<V>
     */
    @SafeVarargs
    public static <V> ImmutableIntObjectMap<V> newImmutableIntMap(ToIntFunction<V> keyFunc, V... values) {
        Validator.nonNull(keyFunc, "keyFunc");
        Validator.nonNull(values, "values");
        return newImmutableIntMap(keyFunc, Arrays.asList(values));
    }

    /**
     * @param <V>      Value type
     * @param keyFunc  IntFunction<V>
     * @param iterable RichIterable<V>
     * @return ImmutableIntObjectMap<V>
     */
    public static <V> ImmutableIntObjectMap<V> newImmutableIntMap(ToIntFunction<V> keyFunc,
                                                                  Iterable<V> iterable) {
        Validator.nonNull(keyFunc, "keyFunc");
        Validator.nonNull(iterable, "iterable");
        return ImmutableIntObjectMapFactoryImpl.INSTANCE.from(iterable, keyFunc::applyAsInt, v -> v);
    }

    /**
     * @param <K>   Key type
     * @param <V>   Value type
     * @param key   key
     * @param value value
     * @return The new ImmutableMap<K, V>
     */
    public static <K, V> ImmutableMap<K, V> newImmutableMap(K key, V value) {
        return ImmutableMapFactoryImpl.INSTANCE.with(key, value);
    }

    /**
     * @param pairs Pair<K, V> array
     * @param <K>   Key type
     * @param <V>   Value type
     * @return The new ImmutableMap<K, V>
     */
    @SafeVarargs
    public static <K, V> ImmutableMap<K, V> newImmutableMap(Pair<K, V>... pairs) {
        if (ArrayUtil.isNullOrEmpty(pairs))
            return ImmutableMapFactoryImpl.INSTANCE.empty();
        switch (pairs.length) {
            case 1 -> {
                return ImmutableMapFactoryImpl.INSTANCE.with(
                        pairs[0].getOne(), pairs[0].getTwo());
            }
            case 2 -> {
                return ImmutableMapFactoryImpl.INSTANCE.with(
                        pairs[0].getOne(), pairs[0].getTwo(),
                        pairs[1].getOne(), pairs[1].getTwo());
            }
            case 3 -> {
                return ImmutableMapFactoryImpl.INSTANCE.with(
                        pairs[0].getOne(), pairs[0].getTwo(),
                        pairs[1].getOne(), pairs[1].getTwo(),
                        pairs[2].getOne(), pairs[2].getTwo());
            }
            case 4 -> {
                return ImmutableMapFactoryImpl.INSTANCE.with(
                        pairs[0].getOne(), pairs[0].getTwo(),
                        pairs[1].getOne(), pairs[1].getTwo(),
                        pairs[2].getOne(), pairs[2].getTwo(),
                        pairs[3].getOne(), pairs[3].getTwo());
            }
            default -> {
                return newImmutableMap(Stream.of(pairs)
                        .collect(Collectors.toMap(Pair::getOne, Pair::getTwo)));
            }
        }
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @param map Map<K, V>
     * @return The new ImmutableMap<K, V>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> ImmutableMap<K, V> newImmutableMap(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty())
            return ImmutableMapFactoryImpl.INSTANCE.empty();
        if (map instanceof ImmutableMap)
            return (ImmutableMap<K, V>) map;
        return ImmutableMapFactoryImpl.INSTANCE.withAll(map);
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Map<K, V> Supplier
     * @return The new ImmutableMap<K, V>
     */
    public static <K, V> ImmutableMap<K, V> newImmutableMap(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null)
            return ImmutableMapFactoryImpl.INSTANCE.empty();
        return newImmutableMap(supplier.get());
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @param map Map<K, V>
     * @return ImmutableSortedMap<K, V>
     */
    @SuppressWarnings("unchecked")
    public static <K, V> ImmutableSortedMap<K, V> newImmutableSortedMap(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty())
            return ImmutableSortedMapFactoryImpl.INSTANCE.empty();
        if (map instanceof ImmutableSortedMap)
            return (ImmutableSortedMap<K, V>) map;
        else
            return ImmutableSortedMapFactoryImpl.INSTANCE.withSortedMap(TreeSortedMap.newMap(map));
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Map<K, V> supplier
     * @return ImmutableSortedMap<K, V>
     */
    public static <K, V> ImmutableSortedMap<K, V> newImmutableSortedMap(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null)
            return ImmutableSortedMapFactoryImpl.INSTANCE.empty();
        return newImmutableSortedMap(supplier.get());
    }

}
