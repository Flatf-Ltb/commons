package io.flatf.common.state.api;

import java.time.LocalTime;

public interface AvailableTime {

    boolean isAvailableAllTime();

    LocalTime[] getStartTimes();

    LocalTime[] getStopTimes();

}
