package io.flatf.common.concurrent.map;

import io.flatf.common.collections.Capacity;
import io.flatf.common.collections.MapUtil;
import org.jctools.maps.NonBlockingHashMap;
import org.jctools.maps.NonBlockingHashMapLong;

public final class NonBlockingMaps {

    private NonBlockingMaps() {
    }

    /**
     * @param <K> Key type
     * @param <V> Value type
     * @return ConcurrentMap<K, V>
     */
    public static <K, V> NonBlockingHashMap<K, V> newHashMap() {
        return new NonBlockingHashMap<>();
    }

    /**
     * @param capacity int
     * @param <K>      Key type
     * @param <V>      Value type
     * @return ConcurrentMap<K, V>
     */
    public static <K, V> NonBlockingHashMap<K, V> newHashMap(int capacity) {
        return new NonBlockingHashMap<>(MapUtil.optimizationCapacity(8, capacity));
    }

    /**
     * @param capacity Capacity
     * @param <K>      Key type
     * @param <V>      Value type
     * @return ConcurrentMap<K, V>
     */
    public static <K, V> NonBlockingHashMap<K, V> newHashMap(Capacity capacity) {
        return new NonBlockingHashMap<>(Capacity.checkAndGet(capacity));
    }

    /**
     * @param <V> Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap() {
        return new NonBlockingHashMapLong<>();
    }

    /**
     * @param capacity int
     * @param <V>      Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap(int capacity) {
        return new NonBlockingHashMapLong<>(MapUtil.optimizationCapacity(8, capacity));
    }

    /**
     * @param capacity Capacity
     * @param <V>      Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap(Capacity capacity) {
        return new NonBlockingHashMapLong<>(Capacity.checkAndGet(capacity));
    }

    /**
     * @param spaceOptimization boolean
     * @param <V>               Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap(boolean spaceOptimization) {
        return new NonBlockingHashMapLong<>(spaceOptimization);
    }

    /**
     * @param capacity          int
     * @param spaceOptimization boolean
     * @param <V>               Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap(int capacity,
                                                               boolean spaceOptimization) {
        return new NonBlockingHashMapLong<>(MapUtil.optimizationCapacity(8, capacity),
                spaceOptimization);
    }

    /**
     * @param capacity          Capacity
     * @param spaceOptimization boolean
     * @param <V>               Value type
     * @return NonBlockingHashMapLong<V>
     */
    public static <V> NonBlockingHashMapLong<V> newLongHashMap(Capacity capacity,
                                                               boolean spaceOptimization) {
        return new NonBlockingHashMapLong<>(Capacity.checkAndGet(capacity),
                spaceOptimization);
    }

}
