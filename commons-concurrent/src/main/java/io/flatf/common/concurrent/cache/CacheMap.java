package io.flatf.common.concurrent.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

/**
 * @param <K>
 * @param <V>
 * @author yellow013
 */
@ThreadSafe
public class CacheMap<K, V> {

    private static final Logger log = Log4j2LoggerFactory.getLogger(CacheMap.class);

    private final LoadingCache<K, V> cache;

    private CacheMap(CacheMapBuilder builder, Function<K, V> refresher) {
        this.cache = CacheBuilder.newBuilder().maximumSize(builder.maximumSize)
                .expireAfterAccess(builder.duration)
                .build(new CacheLoader<>() {
                    @Nonnull
                    @Override
                    public V load(@Nonnull K key) {
                        return refresher.apply(key);
                    }
                });
    }

    public static CacheMapBuilder newBuilder() {
        return new CacheMapBuilder();
    }

    @Nonnull
    public Optional<V> getOptional(K key) {
        try {
            return Optional.of(cache.get(key));
        } catch (ExecutionException e) {
            log.error("CacheMap.get -> [{}] throw {}", key, e.getMessage(), e);
            return Optional.empty();
        }
    }

    public void setUnavailable(K key) {
        cache.invalidate(key);
    }

    /**
     * Builder for GuavaCacheMap
     *
     * @author yellow013
     */
    public static class CacheMapBuilder {

        private long maximumSize = 1024;

        private Duration duration = Duration.ofHours(8);

        public CacheMapBuilder maximumSize(long maximumSize) {
            this.maximumSize = maximumSize;
            return this;
        }

        public CacheMapBuilder expireAfterAccess(Duration duration) {
            this.duration = duration;
            return this;
        }

        public <K, V> CacheMap<K, V> build(@Nonnull Function<K, V> refresher) {
            return new CacheMap<>(this, refresher);
        }

    }

}
