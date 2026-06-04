package io.flatf.common.collections.list;

import io.flatf.common.annotation.AbstractFunction;
import org.eclipse.collections.impl.list.mutable.FastList;

import java.util.ArrayList;
import java.util.List;

public abstract class LimitedList<E> extends LimitedContainer<E> {

    private final List<E> list;

    protected LimitedList(int capacity) {
        super(capacity);
        this.list = newList(capacity);
    }

    /**
     * @param capacity int
     * @return LimitedFastList<E>
     */
    public static <E> LimitedFastList<E> newLimitedFastList(int capacity) {
        return new LimitedFastList<>(capacity);
    }

    /**
     * @param capacity int
     * @return LimitedArrayList<E>
     */
    public static <E> LimitedArrayList<E> newLimitedArrayList(int capacity) {
        return new LimitedArrayList<>(capacity);
    }

    @AbstractFunction
    protected abstract List<E> newList(int capacity);

    @Override
    protected void setTail(int tail, E e) {
        list.set(tail, e);
    }

    @Override
    public E getTail() {
        return list.get(tailIndex());
    }

    @Override
    public E getHead() {
        return list.get(headIndex());
    }

    /**
     * @param <E>
     * @author yellow013
     */
    public static class LimitedFastList<E> extends LimitedList<E> {

        private LimitedFastList(int capacity) {
            super(capacity);
        }

        @Override
        protected List<E> newList(int capacity) {
            return new FastList<>(capacity);
        }

    }

    /**
     * @param <E>
     * @author yellow013
     */
    public static class LimitedArrayList<E> extends LimitedList<E> {

        private LimitedArrayList(int capacity) {
            super(capacity);
        }

        @Override
        protected List<E> newList(int capacity) {
            return new ArrayList<>(capacity);
        }

    }

}
