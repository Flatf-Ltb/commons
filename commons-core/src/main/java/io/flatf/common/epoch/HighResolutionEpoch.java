package io.flatf.common.epoch;

import static io.flatf.common.constant.TimeConst.MICROS_PER_MILLIS;
import static io.flatf.common.constant.TimeConst.NANOS_PER_MICROS;
import static io.flatf.common.constant.TimeConst.NANOS_PER_MILLIS;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.nanoTime;

/**
 * 提供高分辨率Epoch时间
 */
public final class HighResolutionEpoch {

    private static final long NANOS_EPOCH_OFFSET;

    private static final long MICROS_EPOCH_OFFSET;

    static {
        for (int i = 0; i < 10000; i++) {
            // 预热, 确保JIT编译器优化完成
            currentTimeMillis();
            nanoTime();
        }
        // 当前Epoch毫秒数
        long millisEpoch = currentTimeMillis();
        // 当前系统纳秒数
        long baseline = nanoTime();

        // 当前Epoch纳秒数
        long epochNanos = millisEpoch * NANOS_PER_MILLIS;
        // 计算系统纳秒函数与Epoch函数的纳秒偏移量
        NANOS_EPOCH_OFFSET = epochNanos - baseline;

        // 当前Epoch微秒数
        long epochMicros = millisEpoch * MICROS_PER_MILLIS;
        // 计算系统纳秒函数与Epoch函数的微秒偏移量
        MICROS_EPOCH_OFFSET = epochMicros - (baseline / NANOS_PER_MICROS);
    }

    private HighResolutionEpoch() {
    }

    /**
     * 获取<b>[纳秒]</b>单位的[Epoch]时间
     *
     * @return long
     */
    public static long nanos() {
        return nanoTime() + NANOS_EPOCH_OFFSET;
    }

    /**
     * 获取<b>[微秒]</b>单位的[Epoch]时间
     *
     * @return long
     */
    public static long micros() {
        return nanoTime() / NANOS_PER_MICROS + MICROS_EPOCH_OFFSET;
    }

}
