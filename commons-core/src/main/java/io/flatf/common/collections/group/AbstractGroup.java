package io.flatf.common.collections.group;

import org.eclipse.collections.api.map.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static io.flatf.common.collections.ImmutableMaps.newImmutableMap;

/**
 * @param <K>
 * @param <V>
 * @author yellow013
 */
@ThreadSafe
@Immutable
public abstract class AbstractGroup<K, V> implements Group<K, V> {

    protected final ImmutableMap<K, V> savedMap;

    protected final Set<K> keys;

    protected AbstractGroup(Supplier<Map<K, V>> supplier) {
        Map<K, V> map = supplier.get();
        if (map == null)
            throw new IllegalArgumentException("supplier result is null");
        this.keys = map.keySet();
        this.savedMap = newImmutableMap(supplier);
    }

    @Override
    @Nonnull
    public V getMember(@Nonnull K key) throws MemberNotExistException {
        V value = savedMap.get(key);
        if (value == null)
            throw new MemberNotExistException("key -> [" + key + "] no found value");
        return value;
    }

    @Override
    @Nonnull
    public Set<K> getKeys() {
        return keys;
    }

}
