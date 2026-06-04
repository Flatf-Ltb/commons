package io.flatf.common.collections;

import io.flatf.common.util.BitOperator;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public final class MapUtil {

    private MapUtil() {
    }

    /**
     * @param <K>  Key type
     * @param <V>  Value type
     * @param map0 Map<K, V>
     * @param map1 Map<K, V>
     * @return boolean
     */
    public static <K, V> boolean isEquals(Map<K, V> map0, Map<K, V> map1) {
        if (map0 == map1)
            return true;
        else if (map0 == null)
            return false;
        else if (map1 == null)
            return false;
        else
            return map0.equals(map1);
    }

    /**
     * @param <K>   K
     * @param <V>   V
     * @param key   K
     * @param value V
     * @return Map<K, V>
     */
    public static <K, V> Map<K, V> map(@Nonnull K key, @Nonnull V value) {
        Map<K, V> map = new HashMap<>(4);
        map.put(key, value);
        return map;
    }

    /**
     * @param capacity int
     * @return int
     */
    public static int optimizationCapacity(int capacity) {
        return optimizationCapacity(Capacity.DEFAULT_SIZE, capacity);
    }

    /**
     * @param minCapacity int
     * @param capacity    int
     * @return int
     */
    public static int optimizationCapacity(int minCapacity, int capacity) {
        minCapacity = BitOperator.minPow2(minCapacity);
        return capacity < minCapacity ? minCapacity : BitOperator.minPow2(capacity);
    }

    /**
     * @param map M
     * @param <M> Map type
     * @return boolean
     * @throws NullPointerException     npe
     * @throws IllegalArgumentException iae
     */
    public static <M extends Map<?, ?>> boolean nonEmpty(M map)
            throws NullPointerException, IllegalArgumentException {
        return map != null && !map.isEmpty();
    }

}
