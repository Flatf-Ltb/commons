package io.flatf.common.datetime.pattern;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters. <br>
 * The following pattern letters are defined:
 *
 * <pre>
 * Symbol  Meaning                     Presentation      Examples
 * ------  -------                     ------------      -------
 * G       era                         text              AD; Anno Domini; A
 * u       year                        year              2004; 04
 * y       year-of-era                 year              2004; 04
 * D       day-of-year                 number            189
 * M/L     month-of-year               number/text       7; 07; Jul; July; J
 * d       day-of-month                number            10
 *
 * Q/q     quarter-of-year             number/text       3; 03; Q3; 3rd quarter
 * Y       week-based-year             year              1996; 96
 * w       week-of-week-based-year     number            27
 * W       week-of-month               number            4
 * E       day-of-week                 text              Tue; Tuesday; T
 * e/c     localized day-of-week       number/text       2; 02; Tue; Tuesday; T
 * F       week-of-month               number            3
 *
 * a       am-pm-of-day                text              PM
 * h       clock-hour-of-am-pm (1-12)  number            12
 * K       hour-of-am-pm (0-11)        number            0
 * k       clock-hour-of-am-pm (1-24)  number            0
 *
 * H       hour-of-day (0-23)          number            0
 * m       minute-of-hour              number            30
 * s       second-of-minute            number            55
 * S       fraction-of-second          fraction          978
 * A       milli-of-day                number            1234
 * n       nano-of-second              number            987654321
 * N       nano-of-day                 number            1234000000
 *
 * V       time-zone ID                zone-id           America/Los_Angeles; Z; -08:30
 * z       time-zone name              zone-name         Pacific Standard Time; PST
 * O       localized zone-offset       offset-O          GMT+8; GMT+08:00; UTC-08:00;
 * X       zone-offset 'Z' for zero    offset-X          Z; -08; -0830; -08:30; -083015; -08:30:15;
 * x       zone-offset                 offset-x          +0000; -08; -0830; -08:30; -083015; -08:30:15;
 * Z       zone-offset                 offset-Z          +0000; -0800; -08:00;
 *
 * p       pad next                    pad modifier      1
 *
 * '       escape for text             delimiter
 * ''      single quote                literal           '
 * [       optional section start
 * ]       optional section end
 * #       reserved for future use
 * {       reserved for future use
 * }       reserved for future use
 * </pre>
 */
public abstract class SpecifiedPattern<T extends Temporal> implements TemporalPattern<T> {

    private final String pattern;

    private final DateTimeFormatter formatter;

    public SpecifiedPattern(String pattern) {
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    /**
     * @return the pattern string
     */
    @Override
    public String getPattern() {
        return pattern;
    }

    /**
     * @return the DateTimeFormatter instance
     */
    @Override
    public DateTimeFormatter getFormatter() {
        return formatter;
    }

    /**
     * @return T extends Temporal
     */
    protected abstract T provideNow();

    /**
     * @param zoneId ZoneId
     * @return T extends Temporal
     */
    protected abstract T provideNow(ZoneId zoneId);

    /**
     * @param zoneId ZoneId
     * @return String
     */
    @Override
    public String now(ZoneId zoneId) {
        return formatter.format(provideNow(zoneId));
    }

    /**
     * @param temporalObj T extends Temporal
     * @return String
     */
    @Override
    public String fmt(T temporalObj) {
        return formatter.format(temporalObj);
    }

}
