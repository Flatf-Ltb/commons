package io.flatf.common.sequence;

import io.flatf.common.collections.MutableLists;
import io.flatf.common.lang.Validator;
import org.eclipse.collections.api.list.ImmutableList;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

import static io.flatf.common.constant.TimeZoneConst.SYS_DEFAULT;
import static io.flatf.common.datetime.pattern.impl.DateTimePattern.YYYY_MM_DD_HH_MM_SS;

/**
 * 时间窗口序列
 *
 * @author yellow013
 */
public class TimeWindow implements OrderedObject<TimeWindow> {

    private final long epochSecond;

    private final LocalDateTime start;

    private final LocalDateTime end;

    private final ZoneOffset offset;

    private final Duration duration;

    protected TimeWindow(@Nonnull LocalDateTime start, @Nonnull LocalDateTime end,
                         @Nonnull ZoneOffset offset) {
        Validator.nonNull(start, "start");
        Validator.nonNull(end, "end");
        Validator.nonNull(offset, "offset");
        this.start = start;
        this.end = end;
        this.offset = offset;
        this.duration = Duration.between(start, end);
        this.epochSecond = start.toEpochSecond(offset);
    }

    protected TimeWindow(TimeWindow window) {
        this(window.start.plusSeconds(window.duration.getSeconds()),
                window.end.plusSeconds(window.duration.getSeconds()),
                window.offset);
    }

    public static TimeWindow getNext(TimeWindow window) {
        return new TimeWindow(window);
    }

    public static TimeWindow with(@Nonnull LocalDateTime start,
                                  @Nonnull LocalDateTime end,
                                  @Nonnull ZoneOffset offset) {
        return new TimeWindow(start, end, offset);
    }

    /**
     * @param start    LocalTime
     * @param end      LocalTime
     * @param duration Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalTime start,
                                                               @Nonnull LocalTime end,
                                                               @Nonnull Duration duration) {
        return segmentationWindow(start, end, SYS_DEFAULT, duration);
    }

    /**
     * @param start    LocalTime
     * @param end      LocalTime
     * @param offset   ZoneOffset
     * @param duration Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalTime start,
                                                               @Nonnull LocalTime end,
                                                               @Nonnull ZoneOffset offset,
                                                               @Nonnull Duration duration) {
        return segmentationWindow(LocalDate.now(offset), start, end, offset, duration);
    }

    /**
     * @param startDate LocalDate
     * @param start     LocalTime
     * @param end       LocalTime
     * @param duration  Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalDate startDate,
                                                               @Nonnull LocalTime start,
                                                               @Nonnull LocalTime end,
                                                               @Nonnull Duration duration) {
        return segmentationWindow(startDate, start, end, SYS_DEFAULT, duration);
    }

    /**
     * @param startDate LocalDate
     * @param start     LocalTime
     * @param end       LocalTime
     * @param offset    ZoneOffset
     * @param duration  Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalDate startDate,
                                                               @Nonnull LocalTime start,
                                                               @Nonnull LocalTime end,
                                                               @Nonnull ZoneOffset offset,
                                                               @Nonnull Duration duration) {
        return segmentationWindow(LocalDateTime.of(startDate, start),
                LocalDateTime.of(start.isAfter(end)
                        ? startDate.plusDays(1)
                        : startDate, end), offset, duration);
    }

    /**
     * @param start    LocalDateTime
     * @param end      LocalDateTime
     * @param duration Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalDateTime start,
                                                               @Nonnull LocalDateTime end,
                                                               @Nonnull Duration duration) {
        return segmentationWindow(start, end, SYS_DEFAULT, duration);
    }

    /**
     * @param start    LocalDateTime
     * @param end      LocalDateTime
     * @param offset   ZoneOffset
     * @param duration Duration
     * @return ImmutableList<TimeWindow>
     */
    public static ImmutableList<TimeWindow> segmentationWindow(@Nonnull LocalDateTime start,
                                                               @Nonnull LocalDateTime end,
                                                               @Nonnull ZoneOffset offset,
                                                               @Nonnull Duration duration) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("the [end] can not before [start]");
        var between = Duration.between(start, end);
        long seconds = duration.getSeconds();
        long count = between.getSeconds() / seconds;
        var windows = MutableLists.<TimeWindow>newFastList();
        if (count == 0)
            // 时间窗口的持续时间超过起止时间
            windows.add(new TimeWindow(start, end, offset));
        else {
            // 分配第一个时间窗口
            var t0 = start;
            var t1 = start.plusSeconds(seconds);
            for (int i = 0; i < count; i++) {
                windows.add(new TimeWindow(t0, t1, offset));
                // 增加新时间窗口
                t0 = t0.plusSeconds(seconds);
                t1 = t1.plusSeconds(seconds);
                // 检查新时间窗口是否超过结束时间
                if (end.equals(t1) || end.isBefore(t1)) {
                    windows.add(new TimeWindow(t0, end, offset));
                    break;
                }
            }
        }
        return windows.toImmutable();
    }

    /**
     * 检查制定时间是否在时间窗口内
     *
     * @param time LocalDateTime
     * @return boolean
     */
    public boolean isPeriod(@Nonnull LocalDateTime time) {
        return start.equals(time) || (start.isBefore(time) && end.isAfter(time));
    }

    @Override
    public long sequence() {
        return epochSecond;
    }

    public long getEpochSecond() {
        return epochSecond;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public ZoneOffset getOffset() {
        return offset;
    }

    private transient String toStringCache;

    @Override
    public String toString() {
        if (toStringCache == null)
            toStringCache = epochSecond + " -> [" + offset + "][" + YYYY_MM_DD_HH_MM_SS.fmt(start) + " - "
                    + YYYY_MM_DD_HH_MM_SS.fmt(end) + "][" + duration.getSeconds() + "s]";
        return toStringCache;
    }

    public static void main(String[] args) {
        ImmutableList<TimeWindow> windows =
                TimeWindow.segmentationWindow(LocalDate.now(), LocalTime.NOON,
                        LocalTime.of(9, 17), Duration.ofMinutes(5));

        windows.each(System.out::println);
        System.out.println();

        TimeWindow last = windows.getLast();
        TimeWindow last1 = windows.get(windows.size() - 2);

        System.out.println(last1 + " -> " + last1.isPeriod(LocalDateTime.of(2021, 10, 15, 9, 15)));
        System.out.println(last + " -> " + last.isPeriod(LocalDateTime.of(2021, 10, 15, 9, 15)));

    }

}
