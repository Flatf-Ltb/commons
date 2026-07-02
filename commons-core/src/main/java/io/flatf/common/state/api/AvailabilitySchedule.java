package io.flatf.common.state.api;

import java.time.LocalTime;

public interface AvailabilitySchedule {

    boolean isAlwaysAvailable();

    LocalTime[] startTimes();

    LocalTime[] stopTimes();

}
