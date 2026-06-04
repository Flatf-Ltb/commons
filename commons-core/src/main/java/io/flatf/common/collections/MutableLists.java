package io.flatf.common.collections;

import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.api.list.primitive.MutableCharList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

public final class MutableLists {

    private MutableLists() {
    }

    // ******************** primitive list ********************

    /**
     * @return MutableByteList
     */
    public static MutableByteList newByteArrayList() {
        return new ByteArrayList();
    }

    /**
     * @param capacity int
     * @return MutableByteList
     */
    public static MutableByteList newByteArrayList(int capacity) {
        return new ByteArrayList(capacity);
    }

    /**
     * @param values byte[]
     * @return MutableByteList
     */
    public static MutableByteList newByteArrayListWith(byte... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return new ByteArrayList();
        return new ByteArrayList(values);
    }

    /**
     * @return MutableCharList
     */
    public static MutableCharList newCharArrayList() {
        return new CharArrayList();
    }

    /**
     * @param capacity int
     * @return MutableCharList
     */
    public static MutableCharList newCharArrayList(int capacity) {
        return new CharArrayList(capacity);
    }

    /**
     * @param values char[]
     * @return MutableCharList
     */
    public static MutableCharList newCharArrayList(char... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return new CharArrayList();
        return new CharArrayList(values);
    }

    /**
     * @return MutableIntList
     */
    public static MutableIntList newIntArrayList() {
        return new IntArrayList();
    }

    /**
     * @param capacity int
     * @return MutableIntList
     */
    public static MutableIntList newIntArrayList(int capacity) {
        return new IntArrayList(capacity);
    }

    /**
     * @param values int[]
     * @return MutableIntList
     */
    public static MutableIntList newIntArrayListWith(int... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newIntArrayList();
        return new IntArrayList(values);
    }

    /**
     * @return MutableLongList
     */
    public static MutableLongList newLongArrayList() {
        return new LongArrayList();
    }

    /**
     * @param capacity int
     * @return MutableLongList
     */
    public static MutableLongList newLongArrayList(int capacity) {
        return new LongArrayList(capacity);
    }

    /**
     * @param values long[]
     * @return MutableLongList
     */
    public static MutableLongList newLongArrayListWith(long... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newLongArrayList();
        return new LongArrayList(values);
    }

    /**
     * @return MutableDoubleList
     */
    public static MutableDoubleList newDoubleArrayList() {
        return new DoubleArrayList();
    }

    /**
     * @param capacity int
     * @return MutableDoubleList
     */
    public static MutableDoubleList newDoubleArrayList(int capacity) {
        return new DoubleArrayList(capacity);
    }

    /**
     * @param values double[]
     * @return MutableDoubleList
     */
    public static MutableDoubleList newDoubleArrayListWith(double... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newDoubleArrayList();
        return new DoubleArrayList(values);
    }

    /*
     * ******************** object list ********************
     */

    /**
     * @param <E> type
     * @return MutableList
     */
    public static <E> MutableList<E> newFastList() {
        return new FastList<>();
    }

    /**
     * @param <E>      type
     * @param capacity int
     * @return MutableList
     */
    public static <E> MutableList<E> newFastList(int capacity) {
        return new FastList<>(capacity);
    }

    /**
     * @param <E>        type
     * @param collection Collection
     * @return MutableList
     */
    public static <E> MutableList<E> newFastList(@Nullable Collection<E> collection) {
        if (collection == null)
            return newFastList();
        return new FastList<>(collection);
    }

    /**
     * @param <E>      type
     * @param iterator Iterator
     * @return MutableList
     */
    public static <E> MutableList<E> newFastList(@Nullable Iterator<E> iterator) {
        if (iterator != null && iterator.hasNext()) {
            MutableList<E> list = newFastList(Capacity.DEFAULT_SIZE);
            while (iterator.hasNext())
                list.add(iterator.next());
            return list;
        } else
            return newFastList();
    }

    /**
     * @param <E>      type
     * @param iterable Iterable
     * @return MutableList
     */
    public static <E> MutableList<E> newFastList(@Nullable Iterable<E> iterable) {
        if (iterable == null)
            return newFastList();
        return FastList.newList(iterable);
    }

    /**
     * @param <E>    type
     * @param values Object[]
     * @return MutableList
     */
    @SafeVarargs
    public static <E> MutableList<E> newFastList(
            @Nullable E... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return newFastList();
        return FastList.newListWith(values);
    }

}
