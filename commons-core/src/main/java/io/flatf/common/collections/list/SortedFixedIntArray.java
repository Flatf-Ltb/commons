package io.flatf.common.collections.list;

import java.util.Arrays;

public final class SortedFixedIntArray {

    private final int[] saved;

    private final int length;

    private final int lastIndex;

    private int count;

    SortedFixedIntArray(int length) {
        this.saved = new int[length];
        this.length = length;
        this.lastIndex = saved.length - 1;
    }

    public void addLast(int value) {
        if (count == length) {
            for (int i = 0; i < lastIndex; i++)
                saved[i] = saved[i + 1];
            saved[lastIndex] = value;
        } else {
            saved[count] = value;
            count++;
        }
    }

    public int getFirst() {
        if (count == 0)
            return 0;
        return saved[0];
    }

    public int getLast() {
        if (count == length)
            return saved[lastIndex];
        return count == 0 ? 0 : saved[count - 1];
    }

    public int[] getSaved() {
        if (count == length)
            return saved;
        else
            return Arrays.copyOfRange(saved, 0, count);
    }

    public String toString() {
        return Arrays.toString(getSaved());
    }

    public static void main(String[] args) {
        SortedFixedIntArray array = new SortedFixedIntArray(5);
        System.out.println(Arrays.toString(array.getSaved()));
        for (int i = 1; i <= 10; i++) {
            array.addLast(i);
            System.out.println("addLast -> " + i);
            System.out.println("getFirst -> " + array.getFirst());
            System.out.println("getLast -> " + array.getLast());
            System.out.println(array);
        }
    }


}
