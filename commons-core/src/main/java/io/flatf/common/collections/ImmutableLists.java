package io.flatf.common.collections;

import io.flatf.common.util.ArrayUtil;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.impl.list.immutable.ImmutableListFactoryImpl;
import org.eclipse.collections.impl.list.immutable.primitive.ImmutableIntListFactoryImpl;
import org.eclipse.collections.impl.list.immutable.primitive.ImmutableLongListFactoryImpl;

import javax.annotation.Nullable;

public final class ImmutableLists {

    private ImmutableLists() {
    }


    /**
     * @param values int...
     * @return ImmutableIntList
     */
    public static ImmutableIntList newImmutableIntList(int... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableIntListFactoryImpl.INSTANCE.empty();
        return ImmutableIntListFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param values long...
     * @return ImmutableLongList
     */
    public static ImmutableLongList newImmutableLongList(long... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableLongListFactoryImpl.INSTANCE.empty();
        return ImmutableLongListFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @return ImmutableList<E>
     */
    public static <E> ImmutableList<E> newImmutableList() {
        return ImmutableListFactoryImpl.INSTANCE.empty();
    }

    /**
     * @param <E> E
     * @param e   E
     * @return ImmutableList<E>
     */
    public static <E> ImmutableList<E> newImmutableList(@Nullable E e) {
        if (e == null)
            return ImmutableListFactoryImpl.INSTANCE.empty();
        return ImmutableListFactoryImpl.INSTANCE.with(e);
    }

    /**
     * @param <E>    E
     * @param values E...
     * @return ImmutableList<E>
     */
    @SafeVarargs
    public static <E> ImmutableList<E> newImmutableList(@Nullable E... values) {
        if (ArrayUtil.isNullOrEmpty(values))
            return ImmutableListFactoryImpl.INSTANCE.empty();
        return ImmutableListFactoryImpl.INSTANCE.with(values);
    }

    /**
     * @param <E>      E
     * @param iterable Iterable<E>
     * @return ImmutableList<E>
     */
    public static <E> ImmutableList<E> newImmutableList(@Nullable Iterable<E> iterable) {
        if (iterable == null)
            return ImmutableListFactoryImpl.INSTANCE.empty();
        return ImmutableListFactoryImpl.INSTANCE.withAll(iterable);
    }

}
