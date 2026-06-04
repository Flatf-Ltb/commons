package io.flatf.common.constant;

import org.slf4j.Logger;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

public interface TimeZoneConst {

    /**
     * UTC ZoneOffset
     */
    ZoneOffset UTC = ZoneOffset.UTC;

    /**
     * ZoneOffset from runtime
     */
    ZoneOffset SYS_DEFAULT = getZoneOffset(ZoneId.systemDefault());

    /**
     * Chinese Standard Time ZoneOffset
     */
    ZoneOffset CST = getZoneOffset(ZoneId.of("Asia/Shanghai"));

    /**
     * Taiwan Standard Time ZoneOffset
     */
    ZoneOffset TST = getZoneOffset(ZoneId.of("Asia/Taipei"));

    /**
     * Japan Standard Time ZoneOffset
     */
    ZoneOffset JST = getZoneOffset(ZoneId.of("Asia/Tokyo"));

    /**
     * North America Chicago Time ZoneOffset
     */
    ZoneOffset NA_CHICAGO = getZoneOffset(ZoneId.of("America/Chicago"));

    /**
     * @param zoneId ZoneId
     * @return ZoneOffset
     */
    static ZoneOffset getZoneOffset(ZoneId zoneId) {
        if (zoneId instanceof ZoneOffset offset)
            return offset;
        return ZonedDateTime.ofInstant(Instant.EPOCH, zoneId).getOffset();
    }

    /**
     * Show System Available ZoneIds
     */
    static void showAvailableZoneIds() {
        showAvailableZoneIds(null);
    }

    static void showAvailableZoneIds(Logger log) {
        ZoneId.getAvailableZoneIds()
                .stream()
                .sorted(Comparator.naturalOrder())
                .forEachOrdered(log == null
                        ? System.out::println
                        : log::info);
    }

}
