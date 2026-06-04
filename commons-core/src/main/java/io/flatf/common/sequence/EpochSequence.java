package io.flatf.common.sequence;

import io.flatf.common.collections.MutableLists;
import io.flatf.common.epoch.EpochUtil;
import io.flatf.common.util.BitFormatter;
import org.eclipse.collections.api.list.primitive.MutableLongList;

import javax.annotation.concurrent.ThreadSafe;

import static io.flatf.common.util.BitFormatter.longBinaryFormat;
import static io.flatf.common.util.BitOperator.maxValueOfBit;
import static java.lang.System.currentTimeMillis;

/**
 * Use Epoch Time ID
 *
 * <pre>
 * 0b|-----------------epoch milliseconds 47bit-----------|-increment 16bit-|
 * 0b01111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111
 *
 * </pre>
 *
 * @author yellow013
 */
@ThreadSafe
public final class EpochSequence {

    /**
     * 自增位最大限制
     */
    public static final long INCR_LIMIT = 0xFFFF;

    /**
     * 自增位使用bit位数
     */
    public static final int INCR_BITS = 16;

    /**
     * 自增位掩码
     */
    public static final long INCR_MASK = maxValueOfBit(INCR_BITS);

    private static final EpochSequence INSTANCE = new EpochSequence();

    // 第一组占位
    @SuppressWarnings("unused")
    private long p0, p1, p2, p3, p4, p5, p6;
    // 最后使用的Epoch毫秒
    private volatile long lastEpochMillis;
    // 第二组占位
    @SuppressWarnings("unused")
    private long p7, p8, p9, p10, p11, p12, p13;
    // 自增位
    private volatile long incr;
    // 第三组占位
    @SuppressWarnings("unused")
    private long p14, p15, p16, p17, p18, p19, p20;

    private EpochSequence() {
    }

    /**
     * <pre>
     * 0b|-----------------epoch milliseconds ----------------|---increment----|
     * 0b01111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111
     *
     * </pre>
     *
     * @return long
     */
    public static long allocate() {
        return INSTANCE.allocate0();
    }

    /**
     * 生成新ID
     *
     * @return long
     */
    private synchronized long allocate0() {
        long currentEpochMillis = currentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳, 说明系统时钟回退过这个时候应当抛出异常
        if (currentEpochMillis < lastEpochMillis) {
            throw new ClockBackwardException(lastEpochMillis, currentEpochMillis);
        }
        // 如果是同一时间生成的, 则进行毫秒内序列
        if (currentEpochMillis == lastEpochMillis) {
            incr = (incr + 1) & INCR_MASK;
            // 毫秒内序列溢出
            if (incr == 0L)
                // 阻塞到下一个毫秒, 获得新的时间戳
                currentEpochMillis = nextMillis(lastEpochMillis);
        }
        // 时间戳改变, 毫秒内序列重置
        else {
            incr = 0L;
        }
        // 更新最后一次生成ID的时间截
        lastEpochMillis = currentEpochMillis;
        // 返回最终的序列
        return  // 时间戳左移至高位
                (currentEpochMillis << INCR_BITS)
                        // 自增位
                        | incr;
    }

    /**
     * 自旋阻塞到下一个毫秒, 直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long nextMillis(final long lastTimestamp) {
        long timestamp;
        do {
            timestamp = currentTimeMillis();
        } while (timestamp <= lastTimestamp);
        return timestamp;
    }

    /**
     * @param seq long
     * @return long
     */
    public static long parseEpochMillis(long seq) {
        return seq >>> INCR_BITS;
    }

    public static void main(String[] args) {

        MutableLongList longList = MutableLists.newLongArrayList(1000000);
        System.out.println(System.currentTimeMillis());
        long t0 = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            longList.add(EpochSequence.allocate());
        }
        long t1 = System.nanoTime();
        System.out.println(System.currentTimeMillis());
        System.out.println(longList.size() + " count time ms -> " + (t1 - t0) / 1000000);

        System.out.println(longList.get(0));
        System.out.println(EpochSequence.parseEpochMillis(longList.get(0)));
        System.out.println(longList.get(longList.size() - 1));
        System.out.println(EpochSequence.parseEpochMillis(longList.get(longList.size() - 1)));
        System.out.println("========================================");
        System.out.println();

        long epochMillis = EpochUtil.getEpochMillis();
        System.out.println("epoch millis binary: ");
        System.out.println(longBinaryFormat(epochMillis));
        long lmEpochMillis = epochMillis << 16;
        System.out.println("epoch millis << 16 binary: ");
        System.out.println(longBinaryFormat(lmEpochMillis));
        System.out.println("(epoch millis << 16) | 0x7fff binary: ");
        System.out.println(longBinaryFormat(lmEpochMillis | 0x7fff));
        System.out.println("(epoch millis << 16) | 0xffff binary: ");
        System.out.println(longBinaryFormat(lmEpochMillis | 0xffff));
        System.out.println("========================================");
        System.out.println();

        long allocate = EpochSequence.allocate();
        System.out.println(allocate);
        System.out.println(BitFormatter.longBinaryFormat(allocate));
        System.out.println(EpochSequence.parseEpochMillis(allocate));
        System.out.println(BitFormatter.longBinaryFormat(EpochSequence.parseEpochMillis(allocate)));

    }

}
