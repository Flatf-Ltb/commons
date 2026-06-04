package io.flatf.common.concurrent.map;

import io.flatf.common.collections.Capacity;
import org.eclipse.collections.api.block.procedure.primitive.LongProcedure;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableLongObjectMap;
import org.eclipse.collections.api.set.primitive.MutableLongSet;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import static io.flatf.common.collections.MutableLists.newFastList;
import static io.flatf.common.collections.MutableMaps.newLongObjectMap;
import static io.flatf.common.collections.MutableSets.newLongHashSet;

/**
 * @param <V>
 * @author yellow013
 */
@ThreadSafe
public final class ConcurrentLongRangeMap<V> {

    private final MutableLongObjectMap<V> savedMap;
    private final MutableLongSet savedKey;

    public ConcurrentLongRangeMap() {
        this(Capacity.HEX_40);
    }

    public ConcurrentLongRangeMap(Capacity pow2) {
        this.savedMap = newLongObjectMap(pow2.size());
        this.savedKey = newLongHashSet(pow2);

    }

    @Nonnull
    public synchronized ConcurrentLongRangeMap<V> put(long key, V value) {
        savedMap.put(key, value);
        savedKey.add(key);
        return this;
    }

    @CheckForNull
    public V get(long key) {
        return savedMap.get(key);
    }

    @CheckForNull
    public synchronized V remove(long key) {
        savedKey.remove(key);
        return savedMap.remove(key);
    }

    @Nonnull
    public MutableList<V> getAll() {
        return newFastList(savedMap.values());
    }

    public synchronized void clear() {
        savedMap.clear();
        savedKey.clear();
    }

    @Nonnull
    public synchronized MutableList<V> scan(long startPoint, long endPoint) {
        MutableLongSet selectKey = selectKey(startPoint, endPoint);
        MutableList<V> selected = newFastList(selectKey.size());
        operatingSelect(selectKey, key -> selected.add(get(key)));
        return selected;
    }

    @Nonnull
    public synchronized MutableList<V> remove(long startPoint, long endPoint) {
        MutableLongSet selectKey = selectKey(startPoint, endPoint);
        MutableList<V> removed = newFastList(selectKey.size());
        operatingSelect(selectKey, key -> removed.add(remove(key)));
        return removed;
    }

    // TODO 性能优化
    public MutableLongSet selectKey(long startPoint, long endPoint) {
//		MutableLongIterator longIterator = savedKey.longIterator();
//		MutableLongSet longHashSet = MutableSets.newLongHashSet(512);
//		while (longIterator.hasNext()) {
//			long next = longIterator.next();
//			if (next >= startPoint && next <= endPoint)
//				longHashSet.add(next);
//		}
//		return longHashSet;
        return savedKey.select(key -> key >= startPoint && key <= endPoint, newLongHashSet(Capacity.HEX_40));
    }

    private void operatingSelect(MutableLongSet selectKey, LongProcedure func) {
        if (!selectKey.isEmpty())
            selectKey.each(func::accept);
    }

    public static void main(String[] args) {

        long startNano = System.nanoTime();
        ConcurrentLongRangeMap<String> longRangeMap = new ConcurrentLongRangeMap<>(Capacity.HEX_800_000);
        for (long l = 0L; l < 10000L; l++) {
            longRangeMap.put(l, "l == " + l);
        }
        long endNano = System.nanoTime();
        System.out.println((endNano - startNano) / 1000000);

        for (int i = 0; i < 200; i++) {
            long startNano1 = System.nanoTime();
            for (long l = 1000L; l < 1300L; l++) {
                @SuppressWarnings("unused")
                MutableLongSet selectKey = longRangeMap.selectKey(0, l);
                // longRangeMap.get(1000, l);
            }
            long endNano1 = System.nanoTime();
            System.out.println((endNano1 - startNano1) / 1000000);
        }

//		MutableMap<LocalDate, String> unifiedMap = MutableMaps.newUnifiedMap();
//
//		unifiedMap.put(LocalDate.now(), "AAAA");
//		unifiedMap.put(LocalDate.now(), "BBBB");
//		System.out.println(unifiedMap.get(LocalDate.now()));
//		System.out.println(LocalDate.now().hashCode());
//		System.out.println(LocalDate.now().hashCode());
//		
//		System.out.println(LocalDate.now() == LocalDate.now());
    }

}
