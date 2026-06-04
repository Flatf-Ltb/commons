package io.flatf.common.number.recorder;

import io.flatf.common.collections.Capacity;
import io.flatf.common.collections.MutableMaps;
import io.flatf.common.collections.MutableSets;
import org.eclipse.collections.api.map.primitive.MutableLongIntMap;
import org.eclipse.collections.api.set.primitive.MutableLongSet;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class LongRecorder {

	private final MutableLongIntMap longCounter;
	private final MutableLongSet longSet;

	public LongRecorder(Capacity capacity) {
		this.longCounter = MutableMaps.newLongIntMap(capacity.size());
		this.longSet = MutableSets.newLongHashSet(capacity);
	}

	public void put(long value) {
		int count = longCounter.get(value);
		if (count == 0)
			longSet.add(value);
		longCounter.put(value, ++count);
	}

	public void remove(long value) {
		int count = longCounter.get(value);
		if (count == 0)
			return;
		if (count == 1) {
			longSet.remove(value);
			longCounter.remove(value);
		} else
			longCounter.put(value, --count);
	}

	public long max() {
		return longSet.maxIfEmpty(Long.MIN_VALUE);
	}

	public long min() {
		return longSet.minIfEmpty(Long.MAX_VALUE);
	}

}
