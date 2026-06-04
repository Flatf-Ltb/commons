package io.flatf.common.collections.list;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.function.Supplier;

@NotThreadSafe
public final class LimitedArray<E> extends LimitedContainer<E> {

    private final E[] array;

    @SuppressWarnings("unchecked")
    public LimitedArray(int capacity) {
        this(capacity, () -> (E[]) new Object[capacity]);
    }

    public LimitedArray(int capacity, Supplier<E[]> supplier) {
        super(capacity);
        final E[] array = supplier.get();
        if (array.length != capacity)
            throw new IllegalStateException("setting capacity and array length not equal");
        this.array = array;
    }

    @Override
    protected void setTail(int tail, E e) {
        array[tail] = e;
    }

    @Override
    public E getTail() {
        return array[tailIndex()];
    }

    @Override
    public E getHead() {
        return array[headIndex()];
    }

}
