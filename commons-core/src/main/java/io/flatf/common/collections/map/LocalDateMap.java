package io.flatf.common.collections.map;

import io.flatf.common.datetime.DateTimeUtil;
import org.eclipse.collections.api.list.MutableList;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.LocalDate;
import java.util.function.BiPredicate;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

@NotThreadSafe
public final class LocalDateMap<V> extends TemporalMap<LocalDate, V, LocalDateMap<V>> {

    private LocalDateMap(ToLongFunction<LocalDate> keyFunc,
                         UnaryOperator<LocalDate> nextKeyFunc,
                         BiPredicate<LocalDate, LocalDate> hasNextKey) {
        super(keyFunc, nextKeyFunc, hasNextKey);
    }

    private static final BiPredicate<LocalDate, LocalDate> HasNextKey =
            (nextKey, endPoint) -> nextKey.isBefore(endPoint) || nextKey.equals(endPoint);

    /**
     * @return V
     */
    public static <V> LocalDateMap<V> newMap() {
        return new LocalDateMap<>(DateTimeUtil::date,
                key -> key.plusDays(1), HasNextKey);
    }

    @Override
    protected LocalDateMap<V> self() {
        return this;
    }

    public static void main(String[] args) {

        LocalDateMap<String> newMap = newMap();

        LocalDate date = LocalDate.of(2019, 5, 30);

        for (int i = 0; i < 5000; i++) {
            date = date.plusDays(1);
            newMap.put(date, date.toString());
        }

        for (int i = 0; i < 5000; i++) {
            long nanoTime0 = System.nanoTime();
            MutableList<String> scan = newMap.scan(LocalDate.now(), LocalDate.now().plusYears(1));
            long nanoTime1 = System.nanoTime();
            System.out.println((nanoTime1 - nanoTime0) / 1000);
            System.out.println(scan.size());
        }

        newMap.scan(LocalDate.now(), LocalDate.now().plusYears(1)).each(System.out::println);

    }

}
