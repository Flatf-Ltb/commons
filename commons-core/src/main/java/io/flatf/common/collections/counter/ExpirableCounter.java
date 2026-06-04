package io.flatf.common.collections.counter;

import io.flatf.common.collections.Capacity;
import io.flatf.common.collections.MutableMaps;
import io.flatf.common.thread.Sleep;
import org.eclipse.collections.api.iterator.MutableLongIterator;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.map.primitive.MutableLongLongMap;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Duration;

import static io.flatf.common.collections.MutableLists.newLongArrayList;
import static io.flatf.common.collections.MutableMaps.newLongLongMap;

/**
 * 具备过期特性的累加计数器, 可以清除某个特定delta<br>
 * 用于计算单位时间窗口的閥值, 最初设计用于限制单位时间的订单总量<br>
 * 累加值失效有两种情况, 调用remove对value进行修正, 或在过期后自动失效<br>
 * 采用惰性求值, 只在获取当前最新值时排除已过期的值<br>
 * 未进行堆外缓存, 仅在当前JVM进程内有效, JVM重启后, 计数器归零
 * <p>
 * NEXT 增加强一致性, 使用自旋锁
 *
 * @author yellow013
 */
@NotThreadSafe
public final class ExpirableCounter implements Counter<ExpirableCounter> {

    private long value = 0L;

    // 记录时间和Tag
    private final MutableLongLongMap timeToTag;

    // 记录Tag和Delta
    private final MutableLongLongMap tagToDelta;

    // 存储Tag的记录时间
    private final MutableLongList effectiveTimes;

    // 有效时间的纳秒数
    private final long expireNanos;

    /**
     * @param expireTime Duration
     */
    public ExpirableCounter(Duration expireTime) {
        this(expireTime, Capacity.HEX_1_000);
    }

    /**
     * @param expireTime Duration
     * @param capacity   Capacity
     */
    public ExpirableCounter(Duration expireTime, Capacity capacity) {
        this.expireNanos = expireTime.toNanos();
        this.timeToTag = newLongLongMap(capacity.size());
        this.tagToDelta = newLongLongMap(capacity.size());
        this.effectiveTimes = newLongArrayList(capacity.size());
    }

    /**
     * 更新计算值
     *
     * @param delta long
     */
    private void updateValue(long delta) {
        value += delta;
    }

    /**
     * @param tag   long
     * @param delta long
     * @return ExpirableCounter
     */
    @Override
    public ExpirableCounter add(long tag, long delta) {
        if (!tagToDelta.containsKey(tag)) {
            long time = System.nanoTime();
            effectiveTimes.add(time);
            timeToTag.put(time, tag);
            tagToDelta.put(tag, delta);
            updateValue(delta);
        }
        return this;
    }

    /**
     * @return long
     */
    @Override
    public long getValue() {
        final long baseline = System.nanoTime() - expireNanos;
        final MutableLongIterator iterator = effectiveTimes.longIterator();
        while (iterator.hasNext()) {
            long time = iterator.next();
            if (time < baseline) {
                clear(time);
                iterator.remove();
            } else {
                break;
            }
        }
        return value;
    }

    /**
     * @param time long
     */
    private void clear(long time) {
        long tag = timeToTag.get(time);
        long delta = tagToDelta.get(tag);
        updateValue(-delta);
        timeToTag.remove(time);
        tagToDelta.remove(tag);
    }

    /**
     * @param tag long
     * @return ExpirableCounter
     */
    @Override
    public ExpirableCounter removeDelta(long tag) {
        long delta = tagToDelta.get(tag);
        if (delta == 0)
            return this;
        tagToDelta.remove(tag);
        value -= delta;
        return this;
    }

    /**
     * @param tag   long
     * @param delta long
     * @return ExpirableCounter
     */
    @Override
    public ExpirableCounter addDelta(long tag, long delta) {
        long savedDelta = tagToDelta.get(tag);
        if (savedDelta == 0)
            return this;
        tagToDelta.put(tag, savedDelta + delta);
        value += delta;
        return this;
    }

    public static void main(String[] args) {

        ExpirableCounter counter = new ExpirableCounter(Duration.ofMillis(10000), Capacity.HEX_400);

        for (int i = 0; i < 20; i++) {
            counter.add(i, 10);
            Sleep.millis(500);
        }

        for (int i = 0; i < 20; i++) {
            System.out.println(counter.getValue());
            Sleep.millis(2000);
        }

        MutableLongLongMap map = MutableMaps.newLongLongMap(Capacity.HEX_400.size());
        map.put(1, 10);
        System.out.println(-19 - 15);

    }

}
