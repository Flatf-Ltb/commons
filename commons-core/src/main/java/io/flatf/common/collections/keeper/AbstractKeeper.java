package io.flatf.common.collections.keeper;

import io.flatf.common.annotation.AbstractFunction;
import io.flatf.common.collections.Capacity;
import org.eclipse.collections.api.map.ConcurrentMutableMap;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import static io.flatf.common.collections.MutableMaps.newConcurrentMap;

/**
 * @param <K>
 * @param <V>
 * @author yellow013
 */
@ThreadSafe
public abstract class AbstractKeeper<K, V> implements Keeper<K, V> {

    protected final ConcurrentMutableMap<K, V> savedMap;

    protected AbstractKeeper() {
        this(Capacity.HEX_40);
    }

    protected AbstractKeeper(Capacity capacity) {
        this.savedMap = newConcurrentMap(capacity.size());
    }

    @Nonnull
    public V acquire(@Nonnull K key) {
        return savedMap.getIfAbsentPutWithKey(key, this::createWithKey);
    }

    @CheckForNull
    public V get(@Nonnull K key) {
        return savedMap.get(key);
    }

    @AbstractFunction
    protected abstract V createWithKey(K key);

}
