package io.flatf.common.param;

import org.slf4j.Logger;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;

public interface Params {

    boolean getBoolean(ParamKey key);

    int getInt(ParamKey key);

    long getLong(ParamKey key);

    double getDouble(ParamKey key);

    String getString(ParamKey key);

    LocalDate getDate(ParamKey key);

    LocalTime getTime(ParamKey key);

    LocalDateTime getDateTime(ParamKey key);

    ZonedDateTime getZonedDateTime(ParamKey key);

    boolean isImmutable();

    Set<ParamKey> getParamKeys();

    default void showParams() {
        showParams(null);
    }

    default void showParams(Logger log) {
        Set<ParamKey> keys = getParamKeys();
        for (ParamKey key : keys) {
            Object value = switch (key.getValueType()) {
                case STRING -> getString(key);
                case BOOLEAN -> getBoolean(key);
                case DOUBLE -> getDouble(key);
                case INT -> getInt(key);
                case LONG -> getLong(key);
                case DATE -> getDate(key);
                case TIME -> getTime(key);
                case DATETIME -> getDateTime(key);
                case ZONED_DATETIME -> getZonedDateTime(key);
            };
            if (log != null)
                log.info("Key:{} -> Value:{}", key, value);
            else
                System.out.println("Key:" + key + " -> Value:" + value);
        }


    }

    /**
     * @author yellow013
     */
    enum ValueType {

        STRING,

        BOOLEAN,

        DOUBLE,

        INT,

        LONG,

        DATE,

        TIME,

        DATETIME,

        ZONED_DATETIME

    }


    class ParamGettingException extends RuntimeException {

        @Serial
        private static final long serialVersionUID = -862085066844906L;

        public ParamGettingException(String message) {
            super(message);
        }

    }


}
