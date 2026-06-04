package io.flatf.common.number.impl;

import io.flatf.common.number.api.Num;

import static io.flatf.common.number.DoubleArithmetic.add4;
import static io.flatf.common.number.DoubleArithmetic.add8;
import static io.flatf.common.number.DoubleArithmetic.div;
import static io.flatf.common.number.DoubleArithmetic.multiply4;
import static io.flatf.common.number.DoubleArithmetic.multiply8;
import static io.flatf.common.number.DoubleArithmetic.subtraction;

public final class DoubleNum implements Num<DoubleNum> {

    private double value;
    private final boolean isPrecision8;

    private DoubleNum(double value, boolean isPrecision8) {
        this.value = value;
        this.isPrecision8 = isPrecision8;
    }

    public static DoubleNum ofPrecision4(double value) {
        return new DoubleNum(value, false);
    }

    public static DoubleNum ofPrecision8(double value) {
        return new DoubleNum(value, true);
    }

    @Override
    public DoubleNum copy() {
        return new DoubleNum(value, isPrecision8);
    }

    @Override
    public DoubleNum plus(Num<?> augend) {
        if (isPrecision8)
            this.value = add8(value, augend.doubleValue());
        else
            this.value = add4(value, augend.doubleValue());
        return this;
    }

    @Override
    public DoubleNum minus(Num<?> subtrahend) {
        this.value = subtraction(value, subtrahend.doubleValue());
        return this;
    }

    @Override
    public DoubleNum multiplied(Num<?> multiplicand) {
        if (isPrecision8)
            this.value = multiply8(value, multiplicand.doubleValue());
        else
            this.value = multiply4(value, multiplicand.doubleValue());
        return this;
    }

    @Override
    public DoubleNum divided(Num<?> divisor) {
        this.value = div(value, divisor.doubleValue());
        return this;
    }

    @Override
    public DoubleNum pow(int n) {
        this.value = Math.pow(value, n);
        return this;
    }

    @Override
    public boolean isZero() {
        return value == Num.DoubleZero;
    }

    @Override
    public boolean isPositive() {
        return value > Num.DoubleZero;
    }

    @Override
    public boolean isPositiveOrZero() {
        return value >= Num.DoubleZero;
    }

    @Override
    public boolean isNegative() {
        return value < Num.DoubleZero;
    }

    @Override
    public boolean isNegativeOrZero() {
        return value <= Num.DoubleZero;
    }

    @Override
    public boolean isEqual(Num<?> other) {
        return value == other.doubleValue();
    }

    @Override
    public boolean isGreaterThan(Num<?> other) {
        return value > other.doubleValue();
    }

    @Override
    public boolean isGreaterThanOrEqual(Num<?> other) {
        return value >= other.doubleValue();
    }

    @Override
    public boolean isLessThan(Num<?> other) {
        return value < other.doubleValue();
    }

    @Override
    public boolean isLessThanOrEqual(Num<?> other) {
        return value <= other.doubleValue();
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public boolean isNaN() {
        return Double.isNaN(value);
    }

}
