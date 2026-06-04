package io.flatf.common.number.impl;

import io.flatf.common.number.api.Num;

public final class IntNum implements Num<IntNum> {

	private int value;

	private IntNum(int value) {
		this.value = value;
	}

	public static IntNum of(int value) {
		return new IntNum(value);
	}

	@Override
	public IntNum copy() {
		return new IntNum(value);
	}

	@Override
	public IntNum plus(Num<?> augend) {
		value += augend.intValue();
		return this;
	}

	@Override
	public IntNum minus(Num<?> subtrahend) {
		value -= subtrahend.intValue();
		return this;
	}

	@Override
	public IntNum multiplied(Num<?> multiplicand) {
		value *= multiplicand.intValue();
		return this;
	}

	@Override
	public IntNum divided(Num<?> divisor) {
		value /= divisor.intValue();
		return this;
	}

	@Override
	public IntNum pow(int n) {
		value = (int) Math.pow(value, n);
		return this;
	}

	@Override
	public boolean isZero() {
		return value == Num.IntZero;
	}

	@Override
	public boolean isPositive() {
		return value > Num.IntZero;
	}

	@Override
	public boolean isPositiveOrZero() {
		return value >= Num.IntZero;
	}

	@Override
	public boolean isNegative() {
		return value < Num.IntZero;
	}

	@Override
	public boolean isNegativeOrZero() {
		return value <= Num.IntZero;
	}

	@Override
	public boolean isEqual(Num<?> other) {
		return value == other.intValue();
	}

	@Override
	public boolean isGreaterThan(Num<?> other) {
		return value > other.intValue();
	}

	@Override
	public boolean isGreaterThanOrEqual(Num<?> other) {
		return value >= other.intValue();
	}

	@Override
	public boolean isLessThan(Num<?> other) {
		return value < other.intValue();
	}

	@Override
	public boolean isLessThanOrEqual(Num<?> other) {
		return value <= other.intValue();
	}

	@Override
	public double doubleValue() {
		return value;
	}

	@Override
	public int intValue() {
		return value;
	}

	@Override
	public long longValue() {
		return value;
	}

}