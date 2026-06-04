package io.flatf.common.file.filter;

import io.flatf.common.constant.TimeZoneConst;
import io.flatf.common.datetime.pattern.TemporalPattern;
import io.flatf.common.datetime.pattern.impl.DatePattern;
import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileFilter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FileNameDateTimeFilter implements FileFilter {

    private static final Logger log = Log4j2LoggerFactory.getLogger(FileNameDateTimeFilter.class);

    private final TemporalPattern<?> pattern;
    private final ZoneId zoneId;
    private final ZonedDateTime cutoffDate;

    public FileNameDateTimeFilter(@Nonnull LocalDateTime cutoffDate) {
        this(cutoffDate, null, null);
    }

    public FileNameDateTimeFilter(@Nonnull LocalDateTime cutoffDate, ZoneId zoneId) {
        this(cutoffDate, null, zoneId);
    }

    public FileNameDateTimeFilter(@Nonnull LocalDateTime cutoffDate, TemporalPattern<?> pattern) {
        this(cutoffDate, pattern, null);
    }

    public FileNameDateTimeFilter(@Nonnull LocalDateTime cutoffDate, TemporalPattern<?> pattern, ZoneId zoneId) {
        if (pattern == null) {
            pattern = DatePattern.YYYY_MM_DD;
        }
        if (zoneId == null) {
            zoneId = TimeZoneConst.SYS_DEFAULT;
        }
        this.pattern = pattern;
        this.zoneId = zoneId;
        this.cutoffDate = ZonedDateTime.of(cutoffDate, zoneId);
    }

    @Override
    public boolean accept(File file) {
        // parse out the date contained within the filename
        ZonedDateTime datetime;
        try {
            datetime = ZonedDateTime.of(LocalDateTime.parse(file.getName(), pattern.getFormatter()), zoneId);
        } catch (Exception e) {
            // ignore for matching
            return false;
        }
        log.debug("Filename {} contained an embedded date of {}", file.getName(), datetime);

        // does the cutoff date occur before or equal
        if (datetime.isBefore(cutoffDate) || datetime.isEqual(cutoffDate)) {
            log.debug("Filename {} embedded date of {} occurred beforeOrEquals {}",
                    file.getName(), datetime, datetime.isBefore(cutoffDate));
            return true;
        }

        // if we get here, then the date wasn't right
        log.debug("Skipping filename {} since its embedded date of {} occurred after {}",
                file.getName(), datetime, cutoffDate);
        return false;
    }

}
