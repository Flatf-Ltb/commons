package io.flatf.common.collections;

import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.MutableDoubleSet;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.api.set.primitive.MutableLongSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.utility.Iterate;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collector;

import static io.flatf.common.collections.Capacity.checkAndGet;

public final class MutableSets {

    private MutableSets() {

    }

    // ******************** primitive int set ********************

    public static <T> Collector<T, ?, MutableSet<T>> newCollector() {
        return Collectors2.toSet();
    }

    /**
     * @return MutableIntSet
     */
    public static MutableIntSet newIntHashSet() {
        return new IntHashSet();
    }

    /**
     * primitive int set
     *
     * @param capacity Capacity
     * @return MutableIntSet
     */
    public static MutableIntSet newIntHashSet(Capacity capacity) {
        return new IntHashSet(checkAndGet(capacity));
    }

    /**
     * primitive int set
     *
     * @param values int[]
     * @return MutableIntSet
     */
    public static MutableIntSet newIntHashSetWith(int... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newIntHashSet();
        return new IntHashSet(values);
    }

    /**
     * primitive long set
     *
     * @return MutableLongSet
     */
    public static MutableLongSet newLongHashSet() {
        return new LongHashSet();
    }

    /**
     * simple long set
     *
     * @param capacity Capacity
     * @return MutableLongSet
     */
    public static MutableLongSet newLongHashSet(Capacity capacity) {
        return new LongHashSet(checkAndGet(capacity));
    }

    /**
     * simple long set
     *
     * @param values long[]
     * @return MutableLongSet
     */
    public static MutableLongSet newLongHashSetWith(long... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newLongHashSet();
        return new LongHashSet(values);
    }

    /**
     * simple double set
     *
     * @return MutableDoubleSet
     */
    public static MutableDoubleSet newDoubleHashSet() {
        return new DoubleHashSet();
    }

    /**
     * simple double set
     *
     * @param capacity Capacity
     * @return MutableDoubleSet
     */
    public static MutableDoubleSet newDoubleHashSet(Capacity capacity) {
        return new DoubleHashSet(checkAndGet(capacity));
    }

    /**
     * primitive double set
     *
     * @param values double[]
     * @return MutableDoubleSet
     */
    public static MutableDoubleSet newDoubleHashSetWith(double... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newDoubleHashSet();
        return new DoubleHashSet(values);
    }

    /**
     * Set
     *
     * @param <E> E type
     * @return MutableSet<E>
     */
    public static <E> MutableSet<E> newUnifiedSet() {
        return new UnifiedSet<>();
    }

    /**
     * Set
     *
     * @param <E>    E type
     * @param values E[]
     * @return MutableSet<E>
     */
    @SafeVarargs
    public static <E> MutableSet<E> newUnifiedSet(E... values) {
        UnifiedSet<E> set = new UnifiedSet<>();
        if (ArrayUtil.isNullOrEmpty(values))
            return set;
        Collections.addAll(set, values);
        return set;
    }

    /**
     * Set
     *
     * @param <E>      E type
     * @param capacity Capacity
     * @return MutableSet<E>
     */
    public static <E> MutableSet<E> newUnifiedSet(Capacity capacity) {
        return new UnifiedSet<>(checkAndGet(capacity));
    }

    /**
     * Set
     *
     * @param <E>      E type
     * @param iterator Iterator<E>
     * @return MutableSet<E>
     */
    public static <E> MutableSet<E> newUnifiedSet(@Nullable Iterator<E> iterator) {
        MutableSet<E> mutableSet = newUnifiedSet();
        if (iterator != null && iterator.hasNext())
            while (iterator.hasNext())
                mutableSet.add(iterator.next());
        return mutableSet;
    }

    /**
     * Set
     *
     * @param <E>        E type
     * @param collection Collection<E>
     * @return MutableSet<E>
     */
    public static <E> MutableSet<E> newUnifiedSet(@Nullable Collection<E> collection) {
        if (collection == null || collection.isEmpty())
            return newUnifiedSet();
        return new UnifiedSet<>(collection);
    }

    /**
     * SortedSet
     *
     * @param <E> E type
     * @return MutableSortedSet<E>
     */
    public static <E> MutableSortedSet<E> newTreeSortedSet() {
        return new TreeSortedSet<>();
    }

    /**
     * SortedSet
     *
     * @param <E>    E type
     * @param values E[]
     * @return MutableSortedSet<E>
     */
    @SafeVarargs
    public static <E> MutableSortedSet<E> newTreeSortedSet(E... values) {
        MutableSortedSet<E> set = new TreeSortedSet<>();
        if (ArrayUtil.isNullOrEmpty(values))
            return set;
        Collections.addAll(set, values);
        return set;
    }

    /**
     * SortedSet
     *
     * @param <E>        E type
     * @param comparator Comparator<E>
     * @return MutableSortedSet<E>
     */
    public static <E> MutableSortedSet<E> newTreeSortedSet(@Nullable Comparator<E> comparator) {
        if (comparator == null)
            return newTreeSortedSet();
        return new TreeSortedSet<>(comparator);
    }

    /**
     * SortedSet
     *
     * @param <E>      E type
     * @param iterable Iterable<E>
     * @return MutableSortedSet<E>
     */
    public static <E> MutableSortedSet<E> newTreeSortedSet(@Nullable Iterable<E> iterable) {
        if (Iterate.isEmpty(iterable))
            return newTreeSortedSet();
        return new TreeSortedSet<>(iterable);
    }

    /**
     * SortedSet
     *
     * @param <E>        E type
     * @param comparator Comparator<E>
     * @param iterable   Iterable<E>
     * @return MutableSortedSet<E>
     */
    public static <E> MutableSortedSet<E> newTreeSortedSet(@Nullable Comparator<E> comparator,
                                                           @Nullable Iterable<E> iterable) {
        if (comparator != null && iterable != null)
            return new TreeSortedSet<>(comparator, iterable);
        if (comparator != null)
            return newTreeSortedSet(comparator);
        if (iterable != null)
            return newTreeSortedSet(iterable);
        return newTreeSortedSet();
    }

}
