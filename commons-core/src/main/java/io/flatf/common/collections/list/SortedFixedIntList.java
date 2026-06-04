package io.flatf.common.collections.list;

import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.util.Arrays;

public final class SortedFixedIntList {

    private final MutableIntList values;

    private final int length;

    private final int lastIndex;

    private int count;

    SortedFixedIntList(int length) {
        this.values = new IntArrayList(length);
        this.length = length;
        this.lastIndex = length - 1;
    }

    public void addLast(int value) {
        if (count == length) {
            for (int i = 0; i < lastIndex; i++)
                values.set(i, values.get(i + 1));
            values.set(lastIndex, value);
        } else {
            values.add(value);
            count++;
        }
    }

    public int getFirst() {
        return values.getFirst();
    }

    public int getLast() {
        return values.getLast();
    }

    public MutableIntList getValues() {
        return values;
    }

    public ImmutableIntList toImmutable() {
        return values.toImmutable();
    }

    public String toString() {
        return Arrays.toString(values.toArray());
    }

    public static void main(String[] args) {
        SortedFixedIntList array = new SortedFixedIntList(5);
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
