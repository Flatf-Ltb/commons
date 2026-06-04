package io.flatf.common.sequence;

import io.flatf.common.constant.TimeZoneConst;
import io.flatf.common.util.BitFormatter;
import io.flatf.common.util.HexUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static io.flatf.common.constant.TimeZoneConst.UTC;
import static io.flatf.common.epoch.EpochUtil.EPOCH_ZERO;
import static io.flatf.common.util.BitOperator.maxValueOfBit;
import static java.lang.System.currentTimeMillis;
import static java.time.LocalTime.MIN;

/**
 * 通过将63位正整数long类型拆分为三部分实现唯一序列 <br>
 * 时间戳 | 所有者(可以是某个业务ID或者分布式系统上的机器ID) | 自增序列<br>
 *
 * <pre>
 * 0b|------------------- 41 bits ------------------|--10 bits--|-- 12 bits --|
 * 0b|--------------- EPOCH TIMESTAMP --------------|-- OWNER --|- INCREMENT -|
 * 0b01111111_11111111_11111111_11111111_11111111_11 111111_1111 1111_11111111
 *
 * <pre>
 *
 * @author yellow013
 */
public final class SnowflakeAlgo {

    /**
     * 最小基线时间, UTC 2010-01-01 00:00:00.000
     */
    public static final ZonedDateTime MinimumBaseline =
            ZonedDateTime.of(LocalDate.of(2010, 1, 1), MIN, UTC);

    /**
     * 所有者在ID中占的位数
     */
    public static final int NODE_ID_BITS = 10;

    /**
     * 序列在ID中的位数
     */
    public static final int SEQUENCE_BITS = 12;

    /**
     * Epoch时间戳在ID中占的位数
     */
    public static final int TIMESTAMP_BITS = 41;

    /**
     * 所有者ID向左移12位
     */
    public static final int NODE_ID_LEFT_SHIFT = SEQUENCE_BITS;

    /**
     * 时间截向左移22位(10+12)
     */
    public static final int TIMESTAMP_LEFT_SHIFT = NODE_ID_BITS + SEQUENCE_BITS;

    /**
     * 生成序列的掩码, 4095 (0xffff == 4095 == 0b1111_11111111)
     */
    public static final long SEQUENCE_MASK = maxValueOfBit(SEQUENCE_BITS);

    /**
     * 所有者ID的掩码
     */
    public static final long NODE_ID_MASK = maxValueOfBit(NODE_ID_BITS) << NODE_ID_LEFT_SHIFT;

    // 开始时间截 (使用自己业务系统指定的时间)
    private final long baseline;

    // 所有者ID(0~1024)
    private final long nodeId;

    // 毫秒内序列(0~4096)
    private volatile long sequence = 0L;

    // 上次生成ID的时间截
    private volatile long lastTimestamp = -1L;

    /**
     * @param nodeId int
     */
    public SnowflakeAlgo(int nodeId) {
        this(nodeId, ZonedDateTime.ofInstant(Instant.EPOCH, UTC));
    }

    /**
     * @param nodeId int
     * @param start  LocalDate
     */
    public SnowflakeAlgo(int nodeId,
                         @Nonnull final LocalDate start) {
        this(nodeId, start, UTC);
    }

    /**
     * @param nodeId int
     * @param start  LocalDate
     * @param zoneId ZoneId
     */
    public SnowflakeAlgo(int nodeId,
                         @Nullable final LocalDate start,
                         @Nullable final ZoneId zoneId) {
        this(nodeId,
                start == null ? EPOCH_ZERO :
                        ZonedDateTime.of(start, MIN,
                                zoneId == null ? UTC : zoneId));
    }

    /**
     * @param start int
     */
    private SnowflakeAlgo(int nodeId, ZonedDateTime start) {
        if (nodeId < 0 || nodeId > maxValueOfBit(NODE_ID_BITS))
            throw new IllegalArgumentException("ownerId must be [greater than 0] and [less than or equal 1024]");
        this.nodeId = nodeId;
        this.baseline = start.isBefore(EPOCH_ZERO) ? 0 : start.toInstant().toEpochMilli();
    }

    /**
     * 获取基线时间
     *
     * @return long
     */
    public long getBaseline() {
        return baseline;
    }

    /**
     * 解析序列ID中包含的Epoch时间戳
     *
     * @param seq long
     * @return long
     */
    public long parseEpochMillis(long seq) {
        return parseBaselineDiffMillis(seq) + baseline;
    }

    /**
     * 解析序列ID中包含的基线偏移时间
     *
     * @param seq long
     * @return long
     */
    public static long parseBaselineDiffMillis(long seq) {
        return (seq >> (NODE_ID_BITS + SEQUENCE_BITS));
    }

    /**
     * 解析序列ID中包含的节点ID
     *
     * @param seq long
     * @return long
     */
    public static long parseNodeId(long seq) {
        return (seq & NODE_ID_MASK) >> NODE_ID_LEFT_SHIFT;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     * @throws ClockBackwardException e
     */
    public synchronized long next() throws ClockBackwardException {
        long currentTimestamp = currentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳, 说明发生系统时钟回退, 此时抛出异常
        if (currentTimestamp < lastTimestamp) {
            throw new ClockBackwardException(lastTimestamp, currentTimestamp);
        }
        // 如果是同一时间生成的, 则进行毫秒内序列
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0L)
                // 阻塞到下一个毫秒, 获得新的时间戳
                currentTimestamp = nextMillis(lastTimestamp);
        }

        // 时间戳改变, 毫秒内序列重置
        else {
            sequence = 0L;
        }
        // 更新最后一次生成ID的时间截
        lastTimestamp = currentTimestamp;
        // 移位并通过或运算拼接在一起组成63位ID
        return // 计算时间戳于基线时间的偏移量, 并将偏移量左移至高位
                ((currentTimestamp - baseline) << TIMESTAMP_LEFT_SHIFT)
                // 所有者ID左移至中间位
                | (nodeId << NODE_ID_LEFT_SHIFT)
                // 自增位
                | sequence;
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

    public static void main(String[] args) {

        System.out.println(BitFormatter.longBinaryFormat(SEQUENCE_MASK));
        System.out.println(BitFormatter.longBinaryFormat(NODE_ID_MASK));

        SnowflakeAlgo algorithm = new SnowflakeAlgo(20);

        long next = algorithm.next();
        System.out.println(BitFormatter.longBinaryFormat(next));

        long time = algorithm.parseEpochMillis(next);
        System.out.println(System.currentTimeMillis());
        System.out.println(time);
        System.out.println(BitFormatter.longBinaryFormat(time));
        System.out.println(SnowflakeAlgo.parseNodeId(next));

        System.out.println(ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZoneConst.UTC));

        long maxSequence = maxValueOfBit(SEQUENCE_BITS);
        System.out.println(maxSequence);
        System.out.println(BitFormatter.longBinaryFormat(maxSequence));
        System.out.println(HexUtil.toHex(maxSequence));

        long maxOwnerId = maxValueOfBit(NODE_ID_BITS);
        System.out.println(maxOwnerId);
        System.out.println(BitFormatter.longBinaryFormat(maxOwnerId));
        System.out.println(HexUtil.toHex(maxOwnerId));

        System.out.println(BitFormatter.longBinaryFormat(1023L));

    }

}
