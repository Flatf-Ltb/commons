package io.flatf.common.datetime;

import io.flatf.common.collections.MutableLists;
import org.eclipse.collections.api.list.MutableList;

import java.time.Duration;
import java.time.LocalDateTime;

public record TimeWindow(
        LocalDateTime start,
        LocalDateTime end
) {

    public static TimeWindow of(LocalDateTime start, LocalDateTime end) {
        return new TimeWindow(start, end);
    }

    public static MutableList<TimeWindow> splitTime(LocalDateTime start, LocalDateTime end, Duration duration) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("the [end] can not before [start]");
        var timeWindows = MutableLists.<TimeWindow>newFastList();
        var nextStart = start;
        do {
            var nextEnd = nextStart.plus(duration);
            TimeWindow timeWindow;
            if (nextEnd.isAfter(end))
                timeWindow = TimeWindow.of(nextStart, end);
            else
                timeWindow = TimeWindow.of(nextStart, nextEnd);
            timeWindows.add(timeWindow);
            nextStart = timeWindow.end();
        } while (nextStart.isBefore(end));
        return timeWindows;
    }

}

