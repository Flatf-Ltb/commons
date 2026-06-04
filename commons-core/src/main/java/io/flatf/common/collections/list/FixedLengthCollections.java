package io.flatf.common.collections.list;

public final class FixedLengthCollections {

    private FixedLengthCollections() {
        throw new IllegalAccessError("Utility class");
    }

    public static SortedFixedIntArray newIntArray(int length) {
        return new SortedFixedIntArray(length);
    }

    public static SortedFixedLongArray newLongArray(int length) {
        return new SortedFixedLongArray(length);
    }

    public static SortedFixedDoubleArray newDoubleArray(int length) {
        return new SortedFixedDoubleArray(length);
    }

    public static SortedFixedIntList newIntList(int length) {
        return new SortedFixedIntList(length);
    }

    public static SortedFixedLongList newLongList(int length) {
        return new SortedFixedLongList(length);
    }

    public static SortedFixedDoubleList newDoubleList(int length) {
        return new SortedFixedDoubleList(length);
    }

}
