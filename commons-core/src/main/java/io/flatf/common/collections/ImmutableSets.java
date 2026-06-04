package io.flatf.common.collections;

import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableDoubleSet;
import org.eclipse.collections.api.set.primitive.ImmutableIntSet;
import org.eclipse.collections.api.set.primitive.ImmutableLongSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.impl.set.immutable.ImmutableSetFactoryImpl;
import org.eclipse.collections.impl.set.immutable.primitive.ImmutableDoubleSetFactoryImpl;
import org.eclipse.collections.impl.set.immutable.primitive.ImmutableIntSetFactoryImpl;
import org.eclipse.collections.impl.set.immutable.primitive.ImmutableLongSetFactoryImpl;
import org.eclipse.collections.impl.set.sorted.immutable.ImmutableSortedSetFactoryImpl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Stream;

public final class ImmutableSets {

    private ImmutableSets() {
    }

    /**
     * @return E... array
     */
    @SafeVarargs
    public static <E> ImmutableSet<E> from(@Nonnull E... array) {
        return from(Stream.of(array));
    }

    /**
     * @return ImmutableSet<T> set
     */
    public static <E> ImmutableSet<E> from(@Nonnull Stream<E> stream) {
        return ImmutableSetFactoryImpl.INSTANCE.fromStream(stream);
    }

    /**
     * @param values int...
     * @return ImmutableIntSet
     */
    public static ImmutableIntSet newImmutableIntSet(@Nonnull int... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableIntSetFactoryImpl.INSTANCE.empty();
        return ImmutableIntSetFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param values long...
     * @return ImmutableLongSet
     */
    public static ImmutableLongSet newImmutableLongSet(@Nonnull long... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableLongSetFactoryImpl.INSTANCE.empty();
        return ImmutableLongSetFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param values double...
     * @return ImmutableDoubleSet
     */
    public static ImmutableDoubleSet newImmutableDoubleSet(@Nonnull double... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableDoubleSetFactoryImpl.INSTANCE.empty();
        return ImmutableDoubleSetFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param <E>      E type
     * @param iterable Iterable<E>
     * @return ImmutableSet
     */
    public static <E> ImmutableSet<E> newImmutableSet(@Nullable Iterable<E> iterable) {
        if (iterable == null)
            return ImmutableSetFactoryImpl.INSTANCE.empty();
        return ImmutableSetFactoryImpl.INSTANCE.withAll(iterable);
    }

    /**
     * @param <E>    E type
     * @param values E...
     * @return ImmutableSet
     */
    @SafeVarargs
    public static <E> ImmutableSet<E> newImmutableSet(@Nonnull E... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            ImmutableSetFactoryImpl.INSTANCE.empty();
        return ImmutableSetFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param <E>      E type
     * @param iterable Iterable<E>
     * @return ImmutableSortedSet
     */
    public static <E> ImmutableSortedSet<E> newImmutableSortedSet(@Nullable Iterable<E> iterable) {
        if (iterable == null)
            return ImmutableSortedSetFactoryImpl.INSTANCE.empty();
        return ImmutableSortedSetFactoryImpl.INSTANCE.withAll(iterable);
    }

    /**
     * @param <E>    E type
     * @param values E...
     * @return ImmutableSortedSet
     */
    @SafeVarargs
    public static <E> ImmutableSortedSet<E> newImmutableSortedSet(@Nonnull E... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableSortedSetFactoryImpl.INSTANCE.empty();
        return ImmutableSortedSetFactoryImpl.INSTANCE.with(values);
    }

}
