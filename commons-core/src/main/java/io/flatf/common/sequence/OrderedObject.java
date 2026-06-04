package io.flatf.common.sequence;

@FunctionalInterface
public interface OrderedObject<O extends OrderedObject<O>> extends Comparable<O> {

    long sequence();

    @Override
    default int compareTo(O o) {
        return Long.compare(sequence(), o.sequence());
    }

}
