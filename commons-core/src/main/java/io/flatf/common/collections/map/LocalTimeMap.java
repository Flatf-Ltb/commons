package io.flatf.common.collections.map;

import io.flatf.common.datetime.DateTimeUtil;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.LocalTime;
import java.util.function.BiPredicate;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

@NotThreadSafe
public final class LocalTimeMap<V> extends TemporalMap<LocalTime, V, LocalTimeMap<V>> {

    private LocalTimeMap(ToLongFunction<LocalTime> keyFunc,
                         UnaryOperator<LocalTime> nextKeyFunc,
                         BiPredicate<LocalTime, LocalTime> hasNextKey) {
        super(keyFunc, nextKeyFunc, hasNextKey);
    }

    private static final BiPredicate<LocalTime, LocalTime> HasNextKey =
            (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint);

    /**
     * @return V
     */
    public static <V> LocalTimeMap<V> newMapWithHour() {
        return new LocalTimeMap<>(DateTimeUtil::timeOfHour,
                key -> key.plusHours(1), HasNextKey);
    }

    /**
     * @return V
     */
    public static <V> LocalTimeMap<V> newMapWithMinute() {
        return new LocalTimeMap<>(DateTimeUtil::timeOfMinute,
                key -> key.plusMinutes(1), HasNextKey);
    }

    /**
     * @return V
     */
    public static <V> LocalTimeMap<V> newMapWithSecond() {
        return new LocalTimeMap<>(DateTimeUtil::timeOfSecond,
                key -> key.plusSeconds(1), HasNextKey);
    }

    @Override
    protected LocalTimeMap<V> self() {
        return this;
    }

    public static void main(String[] args) {

        System.out.println(Long.MAX_VALUE);
        System.out.println(DateTimeUtil.datetimeOfMillisecond());

    }

}
