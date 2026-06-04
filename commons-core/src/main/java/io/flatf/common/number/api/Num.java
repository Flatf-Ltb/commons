package io.flatf.common.number.api;

import javax.annotation.Nonnull;

public interface Num<N extends Num<N>> extends Comparable<N> {

	int IntZero = 0;
	long LongZero = 0L;
	double DoubleZero = 0.0D;
	
	N copy();

	N plus(Num<?> augend);

	N minus(Num<?> subtrahend);

	N multiplied(Num<?> multiplicand);

	N divided(Num<?> divisor);

	N pow(int n);

	boolean isZero();

	boolean isPositive();

	boolean isPositiveOrZero();

	boolean isNegative();

	boolean isNegativeOrZero();

	boolean isEqual(Num<?> other);

	boolean isGreaterThan(Num<?> other);

	boolean isGreaterThanOrEqual(Num<?> other);

	boolean isLessThan(Num<?> other);

	boolean isLessThanOrEqual(Num<?> other);

	default int min(int other) {
		return Math.min(intValue(), other);
	}

	default int max(int other) {
		return Math.max(intValue(), other);
	}

	default long min(long other) {
		return Math.min(longValue(), other);
	}

	default long max(long other) {
		return Math.max(longValue(), other);
	}

	default double min(double other) {
		return Math.min(doubleValue(), other);
	}

	default double max(double other) {
		return Math.max(doubleValue(), other);
	}

	default boolean isNaN() {
		return false;
	}

	int intValue();

	long longValue();

	double doubleValue();

	@Override
	int hashCode();

	@Override
	String toString();

	@Override
	boolean equals(Object obj);

	@Override
	default int compareTo(@Nonnull N o) {
		return isGreaterThan(o) ? -1 : isLessThan(o) ? 1 : 0;
	}

}
