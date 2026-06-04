package io.flatf.common.collections;

import io.flatf.common.lang.Validator;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.map.ConcurrentMutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.MutableDoubleBooleanMap;
import org.eclipse.collections.api.map.primitive.MutableDoubleIntMap;
import org.eclipse.collections.api.map.primitive.MutableDoubleLongMap;
import org.eclipse.collections.api.map.primitive.MutableIntBooleanMap;
import org.eclipse.collections.api.map.primitive.MutableIntDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.map.primitive.MutableIntLongMap;
import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.MutableLongBooleanMap;
import org.eclipse.collections.api.map.primitive.MutableLongDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableLongIntMap;
import org.eclipse.collections.api.map.primitive.MutableLongLongMap;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.map.primitive.MutableObjectBooleanMap;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.impl.bimap.mutable.HashBiMap;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.eclipse.collections.impl.map.mutable.ConcurrentHashMapUnsafe;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.mutable.primitive.DoubleBooleanHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.DoubleIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.DoubleLongHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntBooleanHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntLongHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.IntObjectHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.LongBooleanHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.LongDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.LongIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.LongLongHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.LongObjectHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectBooleanHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectDoubleHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectLongHashMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import static io.flatf.common.collections.MapUtil.optimizationCapacity;
import static io.flatf.common.util.ArrayUtil.isNullOrEmpty;
import static org.eclipse.collections.impl.collector.Collectors2.toMap;

public final class MutableMaps {

    private MutableMaps() {
    }

    // ******************** key -> int ********************

    /**
     * @return MutableIntIntMap
     */
    public static MutableIntIntMap newIntIntMap() {
        return new IntIntHashMap();
    }

    /**
     * @param capacity int
     * @return MutableIntIntMap
     */
    public static MutableIntIntMap newIntIntMap(int capacity) {
        return new IntIntHashMap(optimizationCapacity(capacity));
    }


    /**
     * @return MutableIntLongMap
     */
    public static MutableIntLongMap newIntLongMap() {
        return new IntLongHashMap();
    }

    /**
     * @param capacity int
     * @return MutableIntLongMap
     */
    public static MutableIntLongMap newIntLongMap(int capacity) {
        return new IntLongHashMap(optimizationCapacity(capacity));
    }


    /**
     * @return MutableIntDoubleMap
     */
    public static MutableIntDoubleMap newIntDoubleMap() {
        return new IntDoubleHashMap();
    }

    /**
     * @param capacity int
     * @return MutableIntDoubleMap
     */
    public static MutableIntDoubleMap newIntDoubleMap(int capacity) {
        return new IntDoubleHashMap(optimizationCapacity(capacity));
    }


    /**
     * @return MutableIntBooleanMap
     */
    public static MutableIntBooleanMap newIntBooleanMap() {
        return new IntBooleanHashMap();
    }

    /**
     * @param capacity int
     * @return MutableIntBooleanMap
     */
    public static MutableIntBooleanMap newIntBooleanMap(int capacity) {
        return new IntBooleanHashMap(optimizationCapacity(capacity));
    }


    /**
     * @param <V> Value type
     * @return MutableIntObjectMap<V>
     */
    public static <V> MutableIntObjectMap<V> newIntObjectMap() {
        return new IntObjectHashMap<>();
    }

    /**
     * @param <V>      Value type
     * @param capacity int
     * @return MutableIntObjectMap<V>
     */
    public static <V> MutableIntObjectMap<V> newIntObjectMap(int capacity) {
        return new IntObjectHashMap<>(optimizationCapacity(capacity));
    }

    /**
     * @param keyFunc ToIntFunction<V>
     * @param values  V...
     * @param <V>     Value type
     * @return MutableIntObjectMap<V>
     */
    @SafeVarargs
    public static <V> MutableIntObjectMap<V> newIntObjectMap(@Nonnull ToIntFunction<V> keyFunc,
                                                             @Nullable V... values) {
        Validator.nonNull(keyFunc, "keyFunc");
        if (isNullOrEmpty(values))
            return newIntObjectMap();
        MutableIntObjectMap<V> map = newIntObjectMap(values.length * 2);
        for (V value : values)
            if (value != null) map.put(keyFunc.applyAsInt(value), value);
        return map;
    }


    // ******************** key -> long ********************


    /**
     * @return MutableLongLongMap
     */
    public static MutableLongLongMap newLongLongMap() {
        return new LongLongHashMap();
    }

    /**
     * @param capacity int
     * @return MutableLongLongMap
     */
    public static MutableLongLongMap newLongLongMap(int capacity) {
        return new LongLongHashMap(optimizationCapacity(capacity));
    }

    /**
     * @return MutableLongIntMap
     */
    public static MutableLongIntMap newLongIntMap() {
        return new LongIntHashMap();
    }

    /**
     * @param capacity int
     * @return MutableLongIntMap
     */
    public static MutableLongIntMap newLongIntMap(int capacity) {
        return new LongIntHashMap(optimizationCapacity(capacity));
    }

    /**
     * @return MutableLongDoubleMap
     */
    public static MutableLongDoubleMap newLongDoubleMap() {
        return new LongDoubleHashMap();
    }

    /**
     * @param capacity int
     * @return MutableLongDoubleMap
     */
    public static MutableLongDoubleMap newLongDoubleMap(int capacity) {
        return new LongDoubleHashMap(optimizationCapacity(capacity));
    }

    /**
     * @return MutableLongBooleanMap
     */
    public static MutableLongBooleanMap newLongBooleanMap() {
        return new LongBooleanHashMap();
    }

    /**
     * @param capacity int
     * @return MutableLongBooleanMap
     */
    public static MutableLongBooleanMap newLongBooleanMap(int capacity) {
        return new LongBooleanHashMap(optimizationCapacity(capacity));
    }

    /**
     * @param <V> Value type
     * @return MutableLongObjectMap<V>
     */
    public static <V> MutableLongObjectMap<V> newLongObjectMap() {
        return new LongObjectHashMap<>();
    }

    /**
     * @param <V>      Value type
     * @param capacity int
     * @return MutableLongObjectMap<V>
     */
    public static <V> MutableLongObjectMap<V> newLongObjectMap(int capacity) {
        return new LongObjectHashMap<>(optimizationCapacity(capacity));
    }

    /*
     * ******************** key -> double ********************
     */

    /**
     * @return MutableDoubleBooleanMap
     */
    public static MutableDoubleBooleanMap newDoubleBooleanMap() {
        return new DoubleBooleanHashMap();
    }

    /**
     * @param capacity int
     * @return MutableDoubleBooleanMap
     */
    public static MutableDoubleBooleanMap newDoubleBooleanMap(int capacity) {
        return new DoubleBooleanHashMap(optimizationCapacity(capacity));
    }


    /**
     * @return MutableDoubleIntMap
     */
    public static MutableDoubleIntMap newDoubleIntMap() {
        return new DoubleIntHashMap();
    }

    /**
     * @param capacity int
     * @return MutableDoubleIntMap
     */
    public static MutableDoubleIntMap newDoubleIntMap(int capacity) {
        return new DoubleIntHashMap(optimizationCapacity(capacity));
    }

    /*
     * ******************** key -> object ********************
     */

    /**
     * @return MutableDoubleLongMap
     */
    public static MutableDoubleLongMap newDoubleLongMap() {
        return new DoubleLongHashMap();
    }

    /**
     * @param capacity int
     * @return MutableDoubleLongMap
     */
    public static MutableDoubleLongMap newDoubleLongMap(int capacity) {
        return new DoubleLongHashMap(optimizationCapacity(capacity));
    }

    /**
     * @param <K> Key type
     * @return MutableObjectBooleanMap<K>
     */
    public static <K> MutableObjectBooleanMap<K> newObjectBooleanMap() {
        return new ObjectBooleanHashMap<>();
    }

    /**
     * @param <K>      Key type
     * @param capacity int
     * @return MutableObjectBooleanMap<K>
     */
    public static <K> MutableObjectBooleanMap<K> newObjectBooleanMap(int capacity) {
        return new ObjectBooleanHashMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @return MutableObjectIntMap<K>
     */
    public static <K> MutableObjectIntMap<K> newObjectIntMap() {
        return new ObjectIntHashMap<>();
    }

    /**
     * @param <K>      Key type
     * @param capacity int
     * @return MutableObjectIntMap<K>
     */
    public static <K> MutableObjectIntMap<K> newObjectIntMap(int capacity) {
        return new ObjectIntHashMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @return MutableObjectLongMap<K>
     */
    public static <K> MutableObjectLongMap<K> newObjectLongMap() {
        return new ObjectLongHashMap<>();
    }

    /**
     * @param <K>      Key type
     * @param capacity int
     * @return MutableObjectLongMap<K>
     */
    public static <K> MutableObjectLongMap<K> newObjectLongMap(int capacity) {
        return new ObjectLongHashMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @return MutableObjectDoubleMap<K>
     */
    public static <K> MutableObjectDoubleMap<K> newObjectDoubleMap() {
        return new ObjectDoubleHashMap<>();
    }

    /**
     * @param <K>      Key type
     * @param capacity int
     * @return MutableObjectDoubleMap<K>
     */
    public static <K> MutableObjectDoubleMap<K> newObjectDoubleMap(int capacity) {
        return new ObjectDoubleHashMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return MutableMap<K, V>
     */
    public static <K, V> MutableMap<K, V> newUnifiedMap() {
        return new UnifiedMap<>();
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param capacity int
     * @return MutableMap<K, V>
     */
    public static <K, V> MutableMap<K, V> newUnifiedMap(int capacity) {
        return new UnifiedMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param map Map<K, V>
     * @param <K> Key type
     * @param <V> Value type
     * @return MutableMap<K, V>
     */
    public static <K, V> MutableMap<K, V> newUnifiedMap(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty()) return new UnifiedMap<>();
        return new UnifiedMap<>(map);
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Supplier<Map<K, V>>
     * @return MutableMap<K, V>
     */
    public static <K, V> MutableMap<K, V> newUnifiedMap(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null) return new UnifiedMap<>();
        return newUnifiedMap(supplier.get());
    }

    /**
     *
     * @param keyFunc Function<V, K>
     * @param stream Stream<V>
     * @return MutableMap<K, V>
     * @param <K> K
     * @param <V> V
     */
    public static <K, V> MutableMap<K, V> newUnifiedMap(@Nonnull Function<V, K> keyFunc,
                                                        @Nonnull Stream<V> stream) {
        return stream.filter(Objects::nonNull)
                .collect(toMap(keyFunc::apply, v -> v));
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return MutableBiMap<K, V>
     */
    public static <K, V> MutableBiMap<K, V> newBiMap() {
        return new HashBiMap<>();
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param capacity int
     * @return MutableBiMap<K, V>
     */
    public static <K, V> MutableBiMap<K, V> newBiMap(int capacity) {
        return new HashBiMap<>(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return MutableSortedMap<K, V>
     */
    public static <K, V> MutableSortedMap<K, V> newSortedMap() {
        return TreeSortedMap.newMap();
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @param map Map<K, V>
     * @return MutableSortedMap<K, V>
     */
    public static <K, V> MutableSortedMap<K, V> newSortedMap(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty()) return TreeSortedMap.newMap();
        return TreeSortedMap.newMap(map);
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Supplier<Map<K, V>>
     * @return MutableSortedMap<K, V>
     */
    public static <K, V> MutableSortedMap<K, V> newSortedMap(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null) return TreeSortedMap.newMap();
        return newSortedMap(supplier.get());
    }

    /*
     * ******************** ConcurrentMutableMap ********************
     */

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMap() {
        return ConcurrentHashMap.newMap();
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param capacity int
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMap(int capacity) {
        return ConcurrentHashMap.newMap(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @param <V> Value type
     * @param map Map<K, V>
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMap(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty()) return ConcurrentHashMap.newMap();
        return ConcurrentHashMap.newMap(map);
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Supplier<Map<K, V>>
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMap(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null) return ConcurrentHashMap.newMap();
        return newConcurrentMap(supplier.get());
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMapUnsafe() {
        return ConcurrentHashMapUnsafe.newMap();
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param capacity int
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMapUnsafe(int capacity) {
        return ConcurrentHashMapUnsafe.newMap(optimizationCapacity(capacity));
    }


    /**
     * @param <K> Key type
     * @param <V> Value type
     * @param map Map<K, V>
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMapUnsafe(@Nullable Map<K, V> map) {
        if (map == null || map.isEmpty()) return ConcurrentHashMapUnsafe.newMap();
        return ConcurrentHashMapUnsafe.newMap(map);
    }

    /**
     * @param <K>      Key type
     * @param <V>      Value type
     * @param supplier Supplier<Map<K, V>>
     * @return ConcurrentMutableMap<K, V>
     */
    public static <K, V> ConcurrentMutableMap<K, V> newConcurrentMapUnsafe(@Nullable Supplier<Map<K, V>> supplier) {
        if (supplier == null) return ConcurrentHashMapUnsafe.newMap();
        return newConcurrentMapUnsafe(supplier.get());
    }

}
