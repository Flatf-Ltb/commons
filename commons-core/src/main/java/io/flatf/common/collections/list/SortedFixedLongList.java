package io.flatf.common.collections.list;

import org.eclipse.collections.api.list.primitive.ImmutableLongList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;

import java.util.Arrays;

public final class SortedFixedLongList {

    private final MutableLongList values;

    private final int length;

    private final int lastIndex;

    private int count;

    SortedFixedLongList(int length) {
        this.values = new LongArrayList(length);
        this.length = length;
        this.lastIndex = length - 1;
    }

    public void addLast(long value) {
        if (count == length) {
            for (int i = 0; i < lastIndex; i++)
                values.set(i, values.get(i + 1));
            values.set(lastIndex, value);
        } else {
            values.add(value);
            count++;
        }
    }

    public long getFirst() {
        return values.getFirst();
    }

    public long getLast() {
        return values.getLast();
    }

    public MutableLongList getValues() {
        return values;
    }

    public ImmutableLongList toImmutable() {
        return values.toImmutable();
    }

    public String toString() {
        return Arrays.toString(values.toArray());
    }

    public static void main(String[] args) {
        SortedFixedLongList array = new SortedFixedLongList(5);
        System.out.println(array);
        for (int i = 1; i <= 10; i++) {
            array.addLast(i);
            System.out.println("addLast -> " + i);
            System.out.println("getFirst -> " + array.getFirst());
            System.out.println("getLast -> " + array.getLast());
            System.out.println(array);
        }
    }


}
