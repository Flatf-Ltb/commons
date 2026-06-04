package io.flatf.common.collections.group;

import io.flatf.common.collections.group.Group.MemberNotExistException;
import org.eclipse.collections.api.map.primitive.ImmutableIntObjectMap;
import org.eclipse.collections.api.map.primitive.IntObjectMap;
import org.eclipse.collections.api.set.primitive.IntSet;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * @param <V>
 * @author yellow013
 */
public abstract class IntGroup<V> {

    protected final ImmutableIntObjectMap<V> savedMap;

    protected final IntSet keys;

    protected IntGroup(Supplier<IntObjectMap<V>> supplier) {
        IntObjectMap<V> map = supplier.get();
        if (map == null)
            throw new IllegalArgumentException("supplier result is null");
        this.keys = map.keySet();
        this.savedMap = map.toImmutable();
    }

    @Nonnull
    public V getMember(int key) throws MemberNotExistException {
        V value = savedMap.get(key);
        if (value == null)
            throw new MemberNotExistException("key -> [" + key + " ] no found value");
        return value;
    }

    @Nonnull
    public IntSet getKeys() {
        return keys;
    }

}
