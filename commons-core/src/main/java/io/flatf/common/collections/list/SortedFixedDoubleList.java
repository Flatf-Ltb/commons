package io.flatf.common.collections.list;

import org.eclipse.collections.api.list.primitive.ImmutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;

import java.util.Arrays;

public final class SortedFixedDoubleList {

    private final MutableDoubleList values;

    private final int length;

    private final int lastIndex;

    private int count;

    SortedFixedDoubleList(int length) {
        this.values = new DoubleArrayList(length);
        this.length = length;
        this.lastIndex = length - 1;
    }

    public void addLast(double value) {
        if (count == length) {
            for (int i = 0; i < lastIndex; i++)
                values.set(i, values.get(i + 1));
            values.set(lastIndex, value);
        } else {
            values.add(value);
            count++;
        }
    }

    public double getFirst() {
        return values.getFirst();
    }

    public double getLast() {
        return values.getLast();
    }

    public MutableDoubleList getValues() {
        return values;
    }

    public ImmutableDoubleList toImmutable() {
        return values.toImmutable();
    }

    public String toString() {
        return Arrays.toString(values.toArray());
    }

    public static void main(String[] args) {
        SortedFixedDoubleList array = new SortedFixedDoubleList(5);
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
