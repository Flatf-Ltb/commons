package io.flatf.common.collections.list;

import java.util.Arrays;

public final class SortedFixedLongArray {

    private final long[] saved;

    private final int length;

    private final int lastIndex;

    private int count;

    SortedFixedLongArray(int length) {
        this.saved = new long[length];
        this.length = length;
        this.lastIndex = saved.length - 1;
    }

    public void addLast(long value) {
        if (count == length) {
            for (int i = 0; i < lastIndex; i++)
                saved[i] = saved[i + 1];
            saved[lastIndex] = value;
        } else {
            saved[count] = value;
            count++;
        }
    }

    public long getFirst() {
        if (count == 0)
            return 0;
        return saved[0];
    }

    public long getLast() {
        if (count == length)
            return saved[lastIndex];
        return count == 0 ? 0 : saved[count - 1];
    }

    public long[] getSaved() {
        if (count == length)
            return saved;
        else
            return Arrays.copyOfRange(saved, 0, count);
    }

    public String toString() {
        return Arrays.toString(getSaved());
    }

}
