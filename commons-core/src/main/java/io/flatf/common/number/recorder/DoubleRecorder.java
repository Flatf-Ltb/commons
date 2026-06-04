package io.flatf.common.number.recorder;

import io.flatf.common.collections.Capacity;
import org.eclipse.collections.api.map.primitive.MutableDoubleIntMap;
import org.eclipse.collections.api.set.primitive.MutableDoubleSet;

import javax.annotation.concurrent.NotThreadSafe;

import static io.flatf.common.collections.MutableMaps.newDoubleIntMap;
import static io.flatf.common.collections.MutableSets.newDoubleHashSet;

/**
 * 有序地记录N个double数值并排序<br>
 * 用于查询最大和最小的值<br>
 * 可以记录重复的值和删除过期的值<br>
 *
 * @author yellow013
 */

@NotThreadSafe
public final class DoubleRecorder {

    private final MutableDoubleIntMap doubleCounter;
    private final MutableDoubleSet doubleSet;

    public DoubleRecorder(Capacity capacity) {
        this.doubleCounter = newDoubleIntMap(capacity.size());
        this.doubleSet = newDoubleHashSet(capacity);
    }

    /**
     * @param value double
     */
    public void put(double value) {
        int count = doubleCounter.get(value);
        if (count == 0)
            doubleSet.add(value);
        doubleCounter.put(value, ++count);
    }

    /**
     * @param value double
     */
    public void remove(double value) {
        int count = doubleCounter.get(value);
        if (count == 0)
            return;
        if (count == 1) {
            doubleSet.remove(value);
            doubleCounter.remove(value);
        } else
            doubleCounter.put(value, --count);
    }

    public double max() {
        return doubleSet.maxIfEmpty(Double.MIN_VALUE);
    }

    public double min() {
        return doubleSet.minIfEmpty(Double.MAX_VALUE);
    }

}
