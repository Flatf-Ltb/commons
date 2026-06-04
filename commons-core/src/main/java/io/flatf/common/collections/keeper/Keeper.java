package io.flatf.common.collections.keeper;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.io.Closeable;

/**
 * @param <K>
 * @param <V>
 * @author yellow013
 */
@ThreadSafe
public interface Keeper<K, V> extends Closeable {

    @Nonnull
    V acquire(@Nonnull K k);

    @CheckForNull
    V get(@Nonnull K k);

}
