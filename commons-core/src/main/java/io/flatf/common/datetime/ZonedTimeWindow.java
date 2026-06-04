package io.flatf.common.datetime;

import io.flatf.common.collections.MutableLists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.time.Duration;
import java.time.ZonedDateTime;

public record ZonedTimeWindow(
        ZonedDateTime start,
        ZonedDateTime end
) {

    /**
     * @param start ZonedDateTime
     * @param end   ZonedDateTime
     * @return ZonedTimeRange
     */
    public static ZonedTimeWindow of(ZonedDateTime start, ZonedDateTime end) {
        return new ZonedTimeWindow(start, end);
    }

    /**
     * @param start    ZonedDateTime
     * @param end      ZonedDateTime
     * @param duration Duration
     * @return MutableList<ZonedTimeRange>
     */
    public static MutableList<ZonedTimeWindow> split(ZonedDateTime start, ZonedDateTime end, Duration duration) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("the [end] can not before [start]");
        var timeWindows = MutableLists.<ZonedTimeWindow>newFastList();
        var nextStart = start;
        do {
            var nextEnd = nextStart.plus(duration);
            ZonedTimeWindow timeWindow;
            if (nextEnd.isAfter(end))
                timeWindow = ZonedTimeWindow.of(nextStart, end);
            else
                timeWindow = ZonedTimeWindow.of(nextStart, nextEnd);
            timeWindows.add(timeWindow);
            nextStart = timeWindow.end();
        } while (nextStart.isBefore(end));
        return timeWindows;
    }

    /**
     * @param start    ZonedDateTime
     * @param end      ZonedDateTime
     * @param duration Duration
     * @return ImmutableList<ZonedTimeRange>
     */
    public static ImmutableList<ZonedTimeWindow> splitWithImmutable(ZonedDateTime start, ZonedDateTime end, Duration duration) {
        return split(start, end, duration).toImmutable();
    }

}
