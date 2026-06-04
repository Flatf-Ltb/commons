package io.flatf.common.concurrent.map;

import io.flatf.common.datetime.DateTimeUtil;
import org.eclipse.collections.api.list.MutableList;
import org.jctools.maps.NonBlockingHashMapLong;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static io.flatf.common.collections.MutableLists.newFastList;

@ThreadSafe
public final class ConcurrentDateTimeMap<K extends Temporal, V> {

    private final ToLongFunction<K> keyFunc;

    private final Function<K, K> nextKeyFunc;

    private final BiPredicate<K, K> hasNextKey;

    private final ConcurrentMap<Long, V> savedMap;

    private ConcurrentDateTimeMap(ToLongFunction<K> keyFunc, Function<K, K> nextKeyFunc,
                                  BiPredicate<K, K> hasNextKey, int initialCapacity) {
        this.keyFunc = keyFunc;
        this.nextKeyFunc = nextKeyFunc;
        this.hasNextKey = hasNextKey;
        this.savedMap = new NonBlockingHashMapLong<>(initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalDate, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDate, V> newDateMap() {
        return newDateMap(128);
    }

    /**
     * @param initialCapacity int
     * @param <V>             Value type
     * @return ConcurrentDateTimeMap<LocalDate, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDate, V> newDateMap(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::date, key -> key.plusDays(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithHour() {
        return newTimeMapWithHour(128);
    }

    /**
     * @param initialCapacity int
     * @param <V>             Value type
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithHour(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::timeOfHour, key -> key.plusHours(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithMinute() {
        return newTimeMapWithMinute(128);
    }

    /**
     * @param initialCapacity int
     * @param <V>             Value type
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithMinute(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::timeOfMinute, key -> key.plusMinutes(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithSecond() {
        return newTimeMapWithSecond(128);
    }

    /**
     * @param <V>             Value type
     * @param initialCapacity int
     * @return ConcurrentDateTimeMap<LocalTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalTime, V> newTimeMapWithSecond(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::timeOfSecond, key -> key.plusSeconds(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithHour() {
        return newDateTimeMapWithHour(128);
    }

    /**
     * @param <V>             Value type
     * @param initialCapacity int
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithHour(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::datetimeOfHour, key -> key.plusHours(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithMinute() {
        return newDateTimeMapWithMinute(128);
    }

    /**
     * @param <V>             Value type
     * @param initialCapacity int
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithMinute(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::datetimeOfMinute, key -> key.plusMinutes(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * @param <V> Value type
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithSecond() {
        return newDateTimeMapWithSecond(128);
    }

    /**
     * @param <V>             Value type
     * @param initialCapacity int
     * @return ConcurrentDateTimeMap<LocalDateTime, V>
     */
    public static <V> ConcurrentDateTimeMap<LocalDateTime, V> newDateTimeMapWithSecond(int initialCapacity) {
        return new ConcurrentDateTimeMap<>(DateTimeUtil::datetimeOfSecond, key -> key.plusSeconds(1),
                (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint), initialCapacity);
    }

    /**
     * put
     *
     * @param key   K
     * @param value V
     * @return ConcurrentDateTimeMap<K, V>
     */
    public ConcurrentDateTimeMap<K, V> put(@Nonnull K key, V value) {
        savedMap.put(keyFunc.applyAsLong(key), value);
        return this;
    }

    /**
     * general get method
     *
     * @param key K
     * @return V
     */
    public V get(@Nonnull K key) {
        return savedMap.get(keyFunc.applyAsLong(key));
    }

    /**
     * general get method
     *
     * @param startPoint K
     * @param endPoint   K
     * @return MutableList<V>
     */
    public synchronized MutableList<V> scan(@Nonnull K startPoint, @Nonnull K endPoint) {
        MutableList<V> list = newFastList(32);
        if (!hasNextKey.test(startPoint, endPoint))
            return result(list, get(endPoint));
        result(list, get(startPoint));
        K nextKey = nextKeyFunc.apply(startPoint);
        while (hasNextKey.test(nextKey, endPoint)) {
            result(list, get(nextKey));
            nextKey = nextKeyFunc.apply(nextKey);
        }
        return list;
    }

    private MutableList<V> result(MutableList<V> list, V value) {
        if (value != null)
            list.add(value);
        return list;
    }

    public static void main(String[] args) {

        ConcurrentDateTimeMap<LocalDate, String> map = ConcurrentDateTimeMap.newDateMap();

        LocalDate date = LocalDate.of(2019, 5, 30);
        for (int i = 0; i < 5000; i++) {
            date = date.plusDays(1);
            map.put(date, date.toString());
        }

        for (int i = 0; i < 5000; i++) {
            long nanoTime0 = System.nanoTime();
            MutableList<String> scan = map.scan(LocalDate.now(), LocalDate.now().plusYears(1));
            long nanoTime1 = System.nanoTime();
            System.out.println((nanoTime1 - nanoTime0) / 1000);
            System.out.println(scan.size());
        }

        map.scan(LocalDate.now(), LocalDate.now().plusYears(1)).each(System.out::println);

    }

}
