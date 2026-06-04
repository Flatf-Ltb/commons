package io.flatf.common.concurrent.cache;

import org.eclipse.collections.api.list.ImmutableList;

import javax.annotation.CheckForNull;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import static io.flatf.common.lang.Validator.nonNull;

/**
 * @param <T>
 * @author yellow013
 */
@ThreadSafe
public final class CacheList<T> {

    private final AtomicReference<Saved> savedRef;

    private final Supplier<ImmutableList<T>> updater;

    /**
     * @author yellow013
     */
    private final class Saved {

        private volatile boolean available;
        private volatile ImmutableList<T> savedList;

        private Saved(boolean available, ImmutableList<T> savedList) {
            this.available = available;
            this.savedList = savedList;
        }

    }

    /**
     * @param updater Supplier<ImmutableList<T>>
     */
    public CacheList(Supplier<ImmutableList<T>> updater) {
        nonNull(updater, "refresher");
        this.updater = updater;
        this.savedRef = new AtomicReference<>(new Saved(true, updater.get()));
    }

    /**
     * @return ImmutableList<T>
     */
    @CheckForNull
    public ImmutableList<T> get() {
        return extractValue(savedRef.get());
    }

    /**
     * @param saved Saved
     * @return ImmutableList<T>
     */
    private ImmutableList<T> extractValue(Saved saved) {
        if (saved.available) {
            return saved.savedList;
        } else {
            return extractValue(savedRef.updateAndGet(save -> {
                save.available = true;
                save.savedList = updater.get();
                return save;
            }));
        }
    }

    /**
     * @return CacheList<T>
     */
    public CacheList<T> setUnavailable() {
        savedRef.updateAndGet(save -> {
            save.available = false;
            return save;
        });
        return this;
    }

}
