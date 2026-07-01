package io.flatf.common.http;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PathParam implements Comparable<PathParam> {

    private final Object name;
    private final Object value;

    public PathParam(@Nonnull Object name, @Nullable Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    private String toStringCache;

    @Override
    public String toString() {
        if (toStringCache == null)
            toStringCache = name + "=" + (value == null ? "" : value);
        return toStringCache;
    }

    @Override
    public int compareTo(@Nonnull PathParam o) {
        if (this.toString().equals(o.toString()))
            return 0;
        return this.toString().compareTo(o.toString());
    }

}
