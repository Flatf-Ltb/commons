package io.flatf.common.collections;

import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.multimap.set.UnifiedSetMultimap;
import org.eclipse.collections.impl.multimap.set.sorted.TreeSortedSetMultimap;

import javax.annotation.Nullable;
import java.util.Comparator;

public final class Multimaps {

    private Multimaps() {
    }

    /**
     * @param <K> K type
     * @param <V> V type
     * @return MutableListMultimap
     */
    public static <K, V> MutableListMultimap<K, V> newFastListMultimap() {
        return FastListMultimap.newMultimap();
    }

    /**
     * @param <K>      K type
     * @param <V>      V type
     * @param iterable Iterable<Pair<K, V>>
     * @return MutableListMultimap
     */
    public static <K, V> MutableListMultimap<K, V> newFastListMultimap(
            @Nullable Iterable<Pair<K, V>> iterable) {
        if (iterable == null)
            return FastListMultimap.newMultimap();
        return FastListMultimap.newMultimap(iterable);
    }

    /**
     * @param <K>   K type
     * @param <V>   V type
     * @param pairs Pair<K, V> array
     * @return MutableListMultimap
     */
    @SafeVarargs
    public static <K, V> MutableListMultimap<K, V> newFastListMultimap(
            @Nullable Pair<K, V>... pairs) {
        if (ArrayUtil.isNullOrEmpty(pairs))
            return FastListMultimap.newMultimap();
        return FastListMultimap.newMultimap(pairs);
    }

    /**
     * @param <K> K type
     * @param <V> V type
     * @return MutableSetMultimap
     */
    public static <K, V> MutableSetMultimap<K, V> newUnifiedSetMultimap() {
        return UnifiedSetMultimap.newMultimap();
    }

    /**
     * @param <K>      K type
     * @param <V>      V type
     * @param iterable Iterable<Pair<K, V>>
     * @return MutableSetMultimap
     */
    public static <K, V> MutableSetMultimap<K, V> newUnifiedSetMultimap(
            @Nullable Iterable<Pair<K, V>> iterable) {
        if (iterable == null)
            return UnifiedSetMultimap.newMultimap();
        return UnifiedSetMultimap.newMultimap(iterable);
    }

    /**
     * @param <K>   K type
     * @param <V>   V type
     * @param pairs Pair<K, V> array
     * @return MutableSetMultimap
     */
    @SafeVarargs
    public static <K, V> MutableSetMultimap<K, V> newUnifiedSetMultimap(
            @Nullable Pair<K, V>... pairs) {
        if (ArrayUtil.isNullOrEmpty(pairs))
            return UnifiedSetMultimap.newMultimap();
        return UnifiedSetMultimap.newMultimap(pairs);
    }

    /**
     * @param <K> K type
     * @param <V> V type
     * @return MutableSortedSetMultimap
     */
    public static <K, V> MutableSortedSetMultimap<K, V> newTreeSortedSetMultimap() {
        return TreeSortedSetMultimap.newMultimap();
    }

    /**
     * @param <K>        K type
     * @param <V>        V type
     * @param comparator Comparator<V>
     * @return MutableSortedSetMultimap
     */
    public static <K, V> MutableSortedSetMultimap<K, V> newTreeSortedSetMultimap(
            @Nullable Comparator<V> comparator) {
        if (comparator == null)
            return TreeSortedSetMultimap.newMultimap();
        return TreeSortedSetMultimap.newMultimap(comparator);
    }

    /**
     * @param <K>      K type
     * @param <V>      V type
     * @param iterable Iterable<Pair<K, V>>
     * @return MutableSortedSetMultimap
     */
    public static <K, V> MutableSortedSetMultimap<K, V> newTreeSortedSetMultimap(
            @Nullable Iterable<Pair<K, V>> iterable) {
        if (iterable == null)
            return TreeSortedSetMultimap.newMultimap();
        return TreeSortedSetMultimap.newMultimap(iterable);
    }

    /**
     * @param <K>   K type
     * @param <V>   V type
     * @param pairs Pair<K, V> array
     * @return MutableSortedSetMultimap
     */
    @SafeVarargs
    public static <K, V> MutableSortedSetMultimap<K, V> newTreeSortedSetMultimap(
            @Nullable Pair<K, V>... pairs) {
        if (ArrayUtil.isNullOrEmpty(pairs))
            return TreeSortedSetMultimap.newMultimap();
        return TreeSortedSetMultimap.newMultimap(pairs);
    }

}
